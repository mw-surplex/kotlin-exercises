package essentials.conditions

fun secondsToPrettyTime(seconds: Int): String {
    return ""
}

fun main() {
    println(secondsToPrettyTime(-1)) // Invalid input
    println(secondsToPrettyTime(0)) // Now
    println(secondsToPrettyTime(45)) // 45 sec
    println(secondsToPrettyTime(60)) // 1 min
    println(secondsToPrettyTime(150)) // 2 min 30 sec
    println(secondsToPrettyTime(1410)) // 23 min 30 sec
    println(secondsToPrettyTime(60 * 60)) // 1 h
    println(secondsToPrettyTime(3665)) // 1 h 1 min 5 sec
}
