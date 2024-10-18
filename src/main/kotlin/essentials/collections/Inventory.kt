package essentials.collections

class Inventory {
    private val products = mutableListOf<Product>()
    private val productIdToProducer =
        mutableMapOf<String, String>()
    private val sellers = mutableSetOf<String>()

    fun addProduct(product: Product, producer: String) {
        // TODO: Add product and assign producer
    }

    fun removeProduct(product: Product) {
        // TODO: Remove product and producer
    }

    fun getProductsCount(): Int = TODO()

    fun hasProduct(product: Product): Boolean = TODO()

    fun hasProducts(): Boolean = TODO()

    fun getProducer(product: Product): String? = TODO()

    fun addSeller(seller: String) {
        // TODO: Add seller
    }

    fun removeSeller(seller: String) {
        // TODO: Remove seller
    }

    fun produceInventoryDisplay(): String {
        var result = "Inventory:\n"
        // TODO: For each product, print name, category, price
        // in the format "{name} ({category}) - ${price}"
        // and print the producer in the format
        // "Produced by: {producer}"
        // TODO: Print sellers in the format
        //  "Sellers: {sellers}"
        return result
    }
}

class Product(
    val id: String,
    val name: String,
    val price: Double,
    val category: String,
)

fun main() {
    val inventory = Inventory()
    println(inventory.hasProducts()) // false
    
    val p1 = Product("P1", "Phone", 599.99, "Electronics")
    val p2 = Product("P2", "Laptop", 1199.99, "Electronics")
    val p3 = Product("P3", "Shirt", 29.99, "Clothing")
    
    inventory.addProduct(p1, "TechCompany")
    inventory.addProduct(p2, "TechCompany")
    inventory.addProduct(p3, "ClothingCompany")
    
    inventory.addSeller("Seller1")
    inventory.addSeller("Seller2")
    
    println(inventory.getProductsCount()) // 3
    println(inventory.hasProduct(p1)) // true
    println(inventory.hasProducts()) // true
    println(inventory.getProducer(p1)) // TechCompany
    
    println(inventory.produceInventoryDisplay())
    // Inventory:
    // Phone (Electronics) - $599.99
    // Produced by: TechCompany
    // Laptop (Electronics) - $1199.99
    // Produced by: TechCompany
    // Shirt (Clothing) - $29.99
    // Produced by: ClothingCompany
    // Sellers: [Seller1, Seller2]
    
    inventory.removeProduct(p2)
    inventory.addSeller("Seller1")
    inventory.removeSeller("Seller2")
    
    println(inventory.getProductsCount()) // 2
    println(inventory.hasProduct(p1)) // true
    println(inventory.hasProduct(p2)) // false
    println(inventory.hasProducts()) // true
    println(inventory.getProducer(p2)) // null
    
    println(inventory.produceInventoryDisplay())
    // Inventory:
    // Phone (Electronics) - $599.99
    // Produced by: TechCompany
    // Shirt (Clothing) - $29.99
    // Produced by: ClothingCompany
    // Sellers: [Seller1]
}
