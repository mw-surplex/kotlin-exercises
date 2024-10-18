package functional.project

import org.junit.After
import org.junit.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class RealUserKeyGeneratorTest {
    private val userRepository = FakeUserRepository()
    private val userKeyGenerator = RealUserKeyGenerator(userRepository)

    @After
    fun cleanup() {
        userRepository.cleanup()
    }

    @Test
    fun `should find public key`() {
        // when
        val result = userKeyGenerator.findPublicKey("name", "surname")

        // then
        assertEquals("namesurname", result)
    }

    @Test
    fun `should try all combinations`() {
        // given
        userRepository.allKeysAreUnavailable()

        // when
        val result = userKeyGenerator.findPublicKey("Michał", "Mazur")

        // then
        assertEquals(
            userRepository.checkedKeys, setOf(
                "michamazur",
                "mazurmicha",
                "micham",
                "mmazur",
                "mazurm",
                "mmicha",
            )
        )
    }
    
    @Test
    fun `should return null if key is shorter than 4 characters`() {
        // when
        val result = userKeyGenerator.findPublicKey("a", "b")

        // then
        assertEquals(null, result)
    }
    
    @Test
    fun `should return another option if key already exists in the database`() {
        // given
        userRepository.addUser(
            UserDto(
                id = "id",
                key = "namesurname",
                email = "email",
                name = "name",
                surname = "surname",
                isAdmin = false,
                passwordHash = "passwordHash",
                creationTime = LocalDateTime.of(2020, 1, 1, 0, 0),
            )
        )

        // when
        val result = userKeyGenerator.findPublicKey("name", "surname")

        // then
        assertEquals("surnamename", result)
    }
}
