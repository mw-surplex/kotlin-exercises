package essentials.nullability

class Client(val personalInfo: PersonalInfo?)
class PersonalInfo(val email: String?)

interface Mailer {
    fun sendMessage(email: String, message: String)
}

// Sends a message if message and client's personal info email are not null
fun sendMessageToClient(client: Client?, message: String?, mailer: Mailer) {
    TODO()
}
