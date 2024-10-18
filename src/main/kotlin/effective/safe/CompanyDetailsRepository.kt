package effective.safe

import kotlinx.coroutines.*
import java.math.BigDecimal
import kotlin.time.measureTime

class CompanyDetailsRepository(
    private val client: CompanyDetailsClient,
    dispatcher: CoroutineDispatcher
) {
    private val details = mutableMapOf<Company, CompanyDetails>()

    suspend fun getDetails(company: Company): CompanyDetails {
        val current = getDetailsOrNull(company)
        if (current == null) {
            val companyDetails = client.fetchDetails(company)
            details[company] = companyDetails
            return companyDetails
        }
        return current
    }

    fun getDetailsOrNull(company: Company): CompanyDetails? = 
        details[company]

    fun getReadyDetails(): Map<Company, CompanyDetails> =
        details
    
    fun clear() {
        details.clear()
    }
}

// Run in main
suspend fun performanceTest(): Unit = coroutineScope {
    val companies = (0 until 100_000).map { Company(it.toString()) }
    val client = FakeCompanyDetailsClient(
        details = buildMap {
            companies.forEach { put(it, CompanyDetails("Company${it.id}", "Address${it.id}", BigDecimal.TEN)) }
        }
    )
    val repository = CompanyDetailsRepository(client, Dispatchers.IO)
    val dispatcher = newFixedThreadPoolContext(100, "test")

    // The time of getting and storing details
    measureTime {
        companies.map { async(dispatcher) { repository.getDetails(it) } }.awaitAll()
    }.also {
        val averageTime = it.inWholeNanoseconds / companies.size
        println("Average time of getting details: $averageTime ns")
    }

    // The time of getting details from cache
    measureTime {
        companies.map { async(dispatcher) { repository.getDetailsOrNull(it) } }.awaitAll()
    }.also {
        val averageTime = it.inWholeNanoseconds / companies.size
        println("Average time of getting details from cache: $averageTime ns")
    }

    // The time of getting all details
    val repeats = 1000
    measureTime {
        coroutineScope {
            repeat(repeats) {
                launch(dispatcher) {
                    repository.getReadyDetails()
                }
            }
        }
    }.also {
        val averageTime = it.inWholeNanoseconds / repeats
        println("Time of getting all details: $averageTime ns")
    }
}

interface CompanyDetailsClient {
    suspend fun fetchDetails(company: Company): CompanyDetails
}

data class CompanyDetails(val name: String, val address: String, val revenue: BigDecimal)
data class Company(val id: String)

class FakeCompanyDetailsClient(
    details: Map<Company, CompanyDetails> = emptyMap(),
    var delay: Long = 0,
) : CompanyDetailsClient {
    private val details: MutableMap<Company, CompanyDetails> = details.toMutableMap()
    var calls = 0
        private set

    override suspend fun fetchDetails(company: Company): CompanyDetails {
        calls++
        delay(delay)
        return details[company] ?: error("Company not found")
    }

    fun setDetails(company: Company, details: CompanyDetails) {
        this.details[company] = details
    }

    fun clear() {
        details.clear()
        delay = 0
    }
}
