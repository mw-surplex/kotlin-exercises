package functional.project

import junit.framework.TestCase
import org.junit.After
import org.junit.Test
import java.time.LocalDateTime

class UserDtoFactoryTest {
    private val timeProvider = FakeTimeProvider()
    private val uuidGenerator = FakeUuidGenerator()
    private val userKeyGenerator = FakeUserKeyGenerator()
    private val userDtoFactory = UserDtoFactory(timeProvider, uuidGenerator, userKeyGenerator)

    @After
    fun cleanup() {
        timeProvider.cleanup()
        uuidGenerator.cleanup()
        userKeyGenerator.cleanup()
    }

    @Test
    fun `should produce user dto`() {
        // given
        val addUser = AddUser(
            email = "email",
            name = "name",
            surname = "surname",
            passwordHash = "passwordHash",
        )
        val expected = UserDto(
            id = "randomId",
            key = "namesurname",
            email = "email",
            name = "name",
            surname = "surname",
            isAdmin = false,
            passwordHash = "passwordHash",
            creationTime = LocalDateTime.of(2020, 1, 1, 0, 0),
        )
        uuidGenerator.constantUuid = "randomId"
        userKeyGenerator.constantKey = "namesurname"
        timeProvider.now = LocalDateTime.of(2020, 1, 1, 0, 0)

        // when
        val result = userDtoFactory.produceUserDto(addUser)

        // then
        TestCase.assertEquals(expected, result)
    }
}
