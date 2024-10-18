package advanced.reflection.dependencyinjection

import kotlin.reflect.KType
import kotlin.reflect.typeOf

// TODO

//data class UserConfiguration(val url: String)
//
//interface UserRepository {
//    fun get(): String
//}
//class RealUserRepository(
//    private val userConfiguration: UserConfiguration,
//) : UserRepository {
//    override fun get(): String =
//        "User from ${userConfiguration.url}"
//}
//
//class UserService(
//    private val userRepository: UserRepository,
//    private val userConfiguration: UserConfiguration,
//) {
//    fun get(): String = "Got ${userRepository.get()}"
//}
//
//fun main() {
//    val registry: Registry = registry {
//        singleton<UserConfiguration> {
//            UserConfiguration("http://localhost:8080")
//        }
//        normal<UserService> {
//            UserService(
//                userRepository = get(),
//                userConfiguration = get(),
//            )
//        }
//        singleton<UserRepository> {
//            RealUserRepository(
//                userConfiguration = get(),
//            )
//        }
//    }
//
//    val userService: UserService = registry.get()
//    println(userService.get())
//    // Got User from http://localhost:8080
//
//    val ur1 = registry.get<UserRepository>()
//    val ur2 = registry.get<UserRepository>()
//    println(ur1 === ur2) // true
//
//    val uc1 = registry.get<UserService>()
//    val uc2 = registry.get<UserService>()
//    println(uc1 === uc2) // false
//}
