package functional.scope

// TODO


//class UserController(
//    private val userRepository: UserRepository,
//    private val logger: Logger,
//) {
//    fun getUser(userId: String) = userRepository
//        .getUser(userId)
//        .orThrow { UserNotFoundException(userId) }
//        .also { logger.log("User with id ${it.id} found") }
//        .toUserJson()
//}
//
//interface UserRepository {
//    fun getUser(userId: String): User?
//}
//
//interface Logger {
//    fun log(message: String)
//}
//
//data class User(
//    val id: String,
//    val name: String,
//)
//
//data class UserJson(
//    val id: String,
//    val name: String,
//)
//
//fun User.toUserJson() = UserJson(
//    id = id,
//    name = name
//)
//
//class UserNotFoundException(userId: String) : Throwable("User $userId not found")
//
//class FakeLogger: Logger {
//    var messages: List<String> = emptyList()
//
//    override fun log(message: String) {
//        this.messages += message
//    }
//}
//
//class FakeUserRepository() : UserRepository {
//    var users: List<User> = emptyList()
//
//    fun addUser(user: User) {
//        users += user
//    }
//
//    override fun getUser(userId: String): User? = users
//        .find { it.id == userId }
//}
