package effective.efficient.tradeProcessingOptimization

import effective.efficient.tradeProcessingOptimization.Filter.*
import effective.efficient.tradeProcessingOptimization.Filter.Relation.*
import effective.efficient.tradeProcessingOptimization.Filter.SnapshotPart.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import kotlin.system.measureTimeMillis

data class TickerSnapshot(
    val ticker: Ticker,
    val snapshot: Snapshot,
)

data class Snapshot(
    val bid: PriceSizeTime?,
    val ask: PriceSizeTime?,
    val last: PriceSizeTime?,
)

data class PriceSizeTime(
    val price: Price,
    val size: Int? = null,
    val time: Long? = null,
)

data class Ticker(val value: String)
data class Price(val value: Float?)

sealed interface Event {
    val ticker: String
}

data class BidEvent(override val ticker: String, val price: Float?, val size: Int?, val time: Long?) : Event
data class AskEvent(override val ticker: String, val price: Float?, val size: Int?, val time: Long?) : Event
data class TradeEvent(override val ticker: String, val price: Float?, val size: Int?, val time: Long?) : Event

val tickers = List(1000) { "Ticker$it" }

// Do not touch this one
class MarketClient {
    fun observe(): Flow<Event> = flow {
        val random = Random(123456789)
        while (true) {
            val event = when ((0..2).random(random)) {
                0 -> BidEvent(
                    tickers.random(random),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000).toFloat(),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000),
                    if (random.nextInt(100) == 1) null else random.nextLong()
                )

                1 -> AskEvent(
                    tickers.random(random),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000).toFloat(),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000),
                    if (random.nextInt(100) == 1) null else random.nextLong()
                )

                else -> TradeEvent(
                    tickers.random(random),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000).toFloat(),
                    if (random.nextInt(100) == 1) null else random.nextInt(100_000),
                    if (random.nextInt(100) == 1) null else random.nextLong()
                )
            }
            emit(event)
        }
    }
}

class MarketRepository(
    private val client: MarketClient,
    backgroundScope: CoroutineScope,
) {
    private val snapshots = ConcurrentHashMap<Ticker, Snapshot>()
    private val updates = MutableSharedFlow<TickerSnapshot>()

    fun observeUpdates() = updates
        .onStart { snapshots.forEach { emit(TickerSnapshot(it.key, it.value)) } }

    init {
        backgroundScope.launch {
            client.observe().collect {
                when (it) {
                    is BidEvent -> {
                        val snapshot = snapshots.getOrPut(Ticker(it.ticker)) { Snapshot(null, null, null) }
                            .copy(bid = PriceSizeTime(Price(it.price), it.size, it.time))
                        snapshots[Ticker(it.ticker)] = snapshot
                        updates.emit(TickerSnapshot(Ticker(it.ticker), snapshot))
                    }

                    is AskEvent -> {
                        val snapshot = snapshots.getOrPut(Ticker(it.ticker)) { Snapshot(null, null, null) }
                            .copy(ask = PriceSizeTime(Price(it.price), it.size, it.time))
                        snapshots[Ticker(it.ticker)] = snapshot
                        updates.emit(TickerSnapshot(Ticker(it.ticker), snapshot))
                    }

                    is TradeEvent -> {
                        val snapshot = snapshots.getOrPut(Ticker(it.ticker)) { Snapshot(null, null, null) }
                            .copy(last = PriceSizeTime(Price(it.price), it.size, it.time))
                        snapshots[Ticker(it.ticker)] = snapshot
                        updates.emit(TickerSnapshot(Ticker(it.ticker), snapshot))
                    }
                }
            }
        }
    }
}

sealed class Filter {
    data object All : Filter()
    class Or(val filters: List<Filter>) : Filter()
    class And(val filters: List<Filter>) : Filter()
    class PrizeCondition(
        val snapshotPart: SnapshotPart,
        val relation: Relation,
        val value: Float,
    ) : Filter()

    class TickerIs(val tickers: List<Ticker>) : Filter()
    class Not(val filter: Filter) : Filter()

    enum class SnapshotPart {
        Ask, Bid, Last, Spread
    }

    enum class Relation {
        GreaterThan, LessThan, Equal
    }

    fun check(tickerSnapshot: TickerSnapshot): Boolean = when (this) {
        All -> true
        is Or -> filters.any { it.check(tickerSnapshot) }
        is And -> filters.all { it.check(tickerSnapshot) }
        is PrizeCondition -> run {
            val snapshotPrize = when (snapshotPart) {
                Ask -> tickerSnapshot.snapshot.ask?.price?.value ?: return@run false
                Bid -> tickerSnapshot.snapshot.bid?.price?.value ?: return@run false
                Last -> tickerSnapshot.snapshot.last?.price?.value ?: return@run false
                Spread -> {
                    val bid = tickerSnapshot.snapshot.bid?.price?.value ?: return@run false
                    val ask = tickerSnapshot.snapshot.ask?.price?.value ?: return@run false
                    ask - bid
                }
            }
            when (relation) {
                GreaterThan -> snapshotPrize > value
                LessThan -> snapshotPrize < value
                Equal -> snapshotPrize == value
            }
        }

        is TickerIs -> tickers.contains(tickerSnapshot.ticker)
        is Not -> !filter.check(tickerSnapshot)
    }
}

class TradeService(
    private val repository: MarketRepository,
) {
    fun observeUpdates(
        filter: Filter? = null,
        tickers: List<String>? = null,
    ) = repository.observeUpdates()
        .filter { tickers == null || it.ticker.value in tickers }
        .filter { filter == null || filter.check(it) }
}

suspend fun main() {
    val client = MarketClient()
    val repository = MarketRepository(client, backgroundScope = CoroutineScope(SupervisorJob()))
    val service = TradeService(repository)
    val filter = Or(
        listOf(
            And(listOf(TickerIs(tickers.take(1).map(::Ticker)), PrizeCondition(Ask, GreaterThan, 99000f))),
            And(listOf(PrizeCondition(Spread, GreaterThan, 99000f))),
        )
    )

    measureTimeMillis {
        service.observeUpdates(
            filter = filter,
            tickers = tickers.take(70)
        ).take(1_000)
            .collect { println(it) }
    }.let { println("Took $it") }
}
