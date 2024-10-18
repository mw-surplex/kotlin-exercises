package essentials.enums

import org.junit.Test
import kotlin.test.assertEquals

class DayOfWeekTest {

    @Test
    fun testMonday() {
        val day = DayOfWeek.MONDAY
        assertEquals("monday", day.dayName)
        assertEquals(DayOfWeek.TUESDAY, day.nextDay())
    }

    @Test
    fun testTuesday() {
        val day = DayOfWeek.TUESDAY
        assertEquals("tuesday", day.dayName)
        assertEquals(DayOfWeek.WEDNESDAY, day.nextDay())
    }
}