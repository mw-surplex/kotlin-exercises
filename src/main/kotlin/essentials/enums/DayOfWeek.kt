package essentials.enums

fun main() {
    val friday: DayOfWeek = DayOfWeek.FRIDAY
    println(friday.dayName) // Friday
    println(friday.isWeekend) // false
    val saturday: DayOfWeek = friday.nextDay()
    println(saturday.dayName) // Saturday
    println(saturday.isWeekend) // true
}

enum class DayOfWeek(val isWeekend: Boolean, val dayName: String) {
    MONDAY(false, "monday"),
    TUESDAY(false, "tuesday"),
    WEDNESDAY(false, "wednesday"),
    THURSDAY(false, "thursday"),
    FRIDAY(false, "friday"),
    SATURDAY(true, "saturday"),
    SUNDAY(true, "sunday");

    fun nextDay(): DayOfWeek =
         DayOfWeek.entries.let {
            it[(it.indexOf(this) + 1) % it.size]
        }
}