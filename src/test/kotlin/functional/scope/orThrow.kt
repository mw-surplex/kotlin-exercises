package functional.scope

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@Suppress("RedundantNullableReturnType")
class OrThrowTest {
//    @Test
//    fun `should throw for null value`() {
//        val value: String? = null
//        val exception = RuntimeException("Value is null")
//        val result = runCatching { value.orThrow { exception } }
//        assertEquals(exception, result.exceptionOrNull())
//    }
//
//    @Test
//    fun `should return value for non-null value`() {
//        val value: String? = "Hello"
//        val result = value.orThrow { RuntimeException("Value is null") }
//        assertEquals("Hello", result)
//    }
//
//    private val value: String? = "Hello"
//    val result = value.orThrow { RuntimeException("Value is null") }
//
//    @Test
//    fun `should specify result type as non-nullable`() {
//        assertFalse(::result.returnType.isMarkedNullable)
//    }
}

//class UserControllerTest {
//    private val logger = FakeLogger()
//    private val userRepository = FakeUserRepository()
//    private val userController = UserController(userRepository, logger)
//
//    @Before
//    fun cleanup() {
//        logger.messages = emptyList()
//        userRepository.users = emptyList()
//    }
//
//    @Test(expected = UserNotFoundException::class)
//    fun `should throw for non-existing user`() {
//        userController.getUser("1")
//    }
//
//    @Test
//    fun `should return user dto for existing user`() {
//        userRepository.addUser(User("1", "John"))
//        val result = userController.getUser("1")
//        assertEquals(UserJson("1", "John"), result)
//    }
//
//    @Test
//    fun `should log for existing user`() {
//        userRepository.addUser(User("1", "John"))
//        userController.getUser("1")
//        assertEquals(listOf("User with id 1 found"), logger.messages)
//    }
//}
