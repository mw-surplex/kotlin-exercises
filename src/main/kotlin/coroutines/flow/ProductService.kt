package coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import java.util.concurrent.atomic.AtomicInteger

class ProductService(
    private val productRepository: ProductRepository,
    backgroundScope: CoroutineScope,
) {
    private val activeObservers = AtomicInteger(0)

    fun observeProducts(categories: Set<String>): Flow<Product> = TODO()

    fun activeObserversCount(): Int = activeObservers.get()
}

interface ProductRepository {
    fun observeProductUpdates(): Flow<String>
    suspend fun fetchProduct(id: String): Product
}

data class Product(
    val id: String,
    val category: String,
    val name: String,
    val price: Double,
)

class FakeProductRepository(
    private val fetchProductsDelay: Long = 0,
) : ProductRepository {
    private val products = listOf(
        Product("1", "electronics", "Smartphone", 500.0),
        Product("2", "books", "Novel", 20.0),
        Product("3", "clothing", "T-Shirt", 15.0)
    )
    private val updates = MutableSharedFlow<String>()

    override fun observeProductUpdates(): Flow<String> = updates

    override suspend fun fetchProduct(id: String): Product {
        if (fetchProductsDelay > 0) {
            delay(fetchProductsDelay)
        }
        return products.first { it.id == id }
    }

    suspend fun updatesHasOccurred(vararg ids: String) {
        ids.forEach { updates.emit(it) }
    }
}
