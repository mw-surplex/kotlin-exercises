package essentials.nullability

import org.junit.Assert.assertThrows
import org.junit.Test
import kotlin.test.assertEquals

class StudentInformationTest {

    @Test
    fun `valid user information`() {
        val user = User("John Doe", 30, EmailAddress("john@example.com"))
        val result = processUserInformation(user)
        val expected = "User John Doe is 30 years old, email: john@example.com"
        assertEquals(expected, result)
    }

    @Test
    fun `null user information`() {
        val result = processUserInformation(null)
        val expected = "Missing user information"
        assertEquals(expected, result)
    }

    @Test
    fun `user with missing age should have age 0`() {
        val user = User("Alice", null, EmailAddress("alice@example.com"))
        val result = processUserInformation(user)
        val expected = "User Alice is 0 years old, email: alice@example.com"
        assertEquals(expected, result)
    }

    @Test
    fun `user with blank email`() {
        val user = User("Bob", 25, EmailAddress(""))
        val result = processUserInformation(user)
        val expected = "Missing email"
        assertEquals(expected, result)
    }

    @Test
    fun `user with null email address`() {
        val user = User("Bob", 25, EmailAddress(null))
        val result = processUserInformation(user)
        val expected = "Missing email"
        assertEquals(expected, result)
    }

    @Test
    fun `user with null email information`() {
        val user = User("Bob", 25, null)
        val result = processUserInformation(user)
        val expected = "Missing email"
        assertEquals(expected, result)
    }

    @Test
    fun `user with missing name should throw exception`() {
        val user = User(null, 40, EmailAddress("email@example.com"))
        assertThrows(IllegalArgumentException::class.java) {
            processUserInformation(user)
        }
    }
}
