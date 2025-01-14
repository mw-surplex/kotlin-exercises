package functional.collections

fun Shop.getWaitingCustomers(): List<Customer> = TODO()

fun Shop.countProductSales(product: Product): Int = TODO()

fun Shop.getCustomers(minAmount: Double): List<Customer> = TODO()

data class Shop(
    val name: String,
    val customers: List<Customer>
)
data class Customer(
    val name: String,
    val city: City,
    val orders: List<Order>
)
data class Order(
    val products: List<Product>,
    val isDelivered: Boolean
)
data class Product(
    val name: String,
    val price: Double
)
data class City(
    val name: String
)
