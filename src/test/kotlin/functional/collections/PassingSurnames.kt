package functional.collections

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PassingSurnamesTest {
    @Test
    fun `should return passing surnames`() {
        val students = listOf(
            StudentJson("John", "Smith", 60.0, 20),
            StudentJson("Jane", "Doe", 45.0, 20),
            StudentJson("Ivan", "Ivanov", 60.0, 10),
            StudentJson("John", "Doe", 30.0, 10),
            StudentJson("Jake", "Simonson", 80.0, 20),
        )
        assertEquals(
            listOf("Smith", "Simonson"),
            students.getPassingSurnames()
        )
    }
}
