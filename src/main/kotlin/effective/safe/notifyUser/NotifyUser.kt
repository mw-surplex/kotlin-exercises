package effective.safe.notifyUser

fun Notifier.notifyUser(user: User?) {
    TODO()
}

data class User(
    val id: Int,
    val name: String?,
    var surname: String?
)

class IncorrectId : Error()

interface Notifier {

    /**
     * Indicate instance readiness to notify users
     */
    val initialized: Boolean

    /**
     * Notifies person
     * @param id Is an id of user we want to notify
     * @return Was the operation successful
     */
    fun notifyPerson(id: Int): Boolean

    /**
     * Checks if we can send message to the following id
     */
    fun checkId(id: Int): Boolean
}
