package functional.project

interface UserKeyGenerator {
    fun findPublicKey(name: String, surname: String): String?
}

class RealUserKeyGenerator(
    private val userRepository: UserRepository,
) : UserKeyGenerator {
    // Should find a key that is not used by any other user.
    // Should try the following combinations of name and surname:
    // - name + surname
    // - surname + name
    // - name + first letter of surname
    // - first letter of name + surname
    // - surname + first letter of name
    // - first letter of surname + name
    // Should remove all non-alphanumeric characters from the key.
    // Should lowercase the key.
    // Should trim the key.
    // Should return null if the key is shorter than 4 characters.
    // Should return null if key already exists in the database.
    override fun findPublicKey(name: String, surname: String): String? = TODO()
}

class FakeUserKeyGenerator : UserKeyGenerator {
    var constantKey: String? = null

    fun cleanup() {
        constantKey = null
    }

    override fun findPublicKey(name: String, surname: String): String? = constantKey
}
