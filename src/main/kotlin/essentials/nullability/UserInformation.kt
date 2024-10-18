package essentials.nullability

fun processUserInformation(user: User?): String {
    return ""
}

data class EmailAddress(val email: String?)

data class User(
    val name: String?,
    val age: Int?,
    val email: EmailAddress?
)

fun main() {
    println(processUserInformation(null))
    // Missing user information
    
    val user1 = User(
        "John",
        30,
        EmailAddress("john@example.com")
    )
    println(processUserInformation(user1))
    // User John is 30 years old, email: john@example.com
    
    val user2 = User(
        "Alice",
        null,
        EmailAddress("alice@example.com")
    )
    println(processUserInformation(user2))
    // User Alice is 0 years old, email: alice@example.com
    
    val user3 = User(
        "Bob",
        25,
        EmailAddress("") // or EmailAddress(null) or null
    )
    println(processUserInformation(user3))
    // Missing email
    
    val user6 = User(
        null,
        40,
        EmailAddress("jake@example.com")
    )
    println(processUserInformation(user6))
    // IllegalArgumentException
}
