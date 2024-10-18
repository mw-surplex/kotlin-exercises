package essentials.extensions

import java.time.LocalDateTime

// TODO

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

// TODO: Implement extensions to convert User to UserJson and vice versa,
//  and to create Centimeters from Int

fun main() {
    //val user = User(
    //    username = "alex",
    //    email = Email("alex@example.com"),
    //    registrationDate = LocalDateTime
    //        .of(1410, 7, 15, 10, 13),
    //    height = 170.cm,
    //)
    //val userJson = user.toUserJson()
    //println(userJson)
    //// UserJson(username=alex, email=alex@example.com,
    //// registrationDate=1410-07-15T10:13, heightCm=170)
    //val user2 = userJson.toUser()
    //println(user2) // User(username=alex,
    //// email=Email(value=alex@example.com),
    //// registrationDate=1410-07-15T10:13,
    //// height=Centimeters(value=170))
}
