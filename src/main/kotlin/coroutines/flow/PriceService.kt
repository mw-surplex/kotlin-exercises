package coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import java.math.BigDecimal

class PriceService(
    priceRepository: PriceRepository,
    backgroundScope: CoroutineScope,
) {
    fun observePrices(): Flow<Map<ProductId, PriceConfig>> =TODO()

    fun currentPrices(): Map<ProductId, PriceConfig> = TODO()
}

interface PriceRepository {
    fun observeUpdates(): Flow<Map<ProductId, PriceConfig>>
}

data class ProductId(val value: String)

class PriceConfig(
    val prices: Map<String, Map<Currencey, BigDecimal>>,
)

enum class Currencey {
    USD, EUR, GBP
}

class FakePriceRepository : PriceRepository {
    private val observer = MutableSharedFlow<Map<ProductId, PriceConfig>>()
    private var observersCount = 0

    override fun observeUpdates(): Flow<Map<ProductId, PriceConfig>> = observer
        .onSubscription { observersCount++ }

    suspend fun emitPrices(prices: Map<ProductId, PriceConfig>) {
        observer.emit(prices)
    }

    fun observersCount() = observersCount
}
