package essentials.extensions

import java.time.LocalDateTime

// https://kt.academy/article/e_conversion_and_measurement_unit_creation

data class User(
    val username: String,
    val email: Email,
    val registrationDate: LocalDateTime,
    val height: Centimeters,
)

data class Email(val value: String)

data class Centimeters(val value: Int)

data class UserJson(
    val username: String,
    val email: String,
    val registrationDate: String,
    val heightCm: Int,
)

fun User.toUserJson() = UserJson(username, email.value, registrationDate.toString(), height.value)

fun UserJson.toUser() = User(username, Email(email), LocalDateTime.parse(registrationDate), heightCm.cm)

val Int.cm get() = Centimeters(this)

fun main() {
    val user = User(
        username = "alex",
        email = Email("alex@example.com"),
        registrationDate = LocalDateTime
            .of(2024, 7, 15, 10, 13),
        height = 170.cm,
    )
    val userJson = user.toUserJson()
    println(userJson)
    UserJson(username="alex", email="alex@example.com", registrationDate="2024-07-15T10:13", heightCm=170)
    val user2 = userJson.toUser()
    println(user2)
}
