package coroutines.test

import kotlinx.coroutines.*

class UserDetailsRepository(
    private val client: UserDataClient,
    private val userDatabase: UserDetailsDatabase,
    private val backgroundScope: CoroutineScope,
) {
    suspend fun getUserDetails(): UserDetails = coroutineScope {
        val stored = userDatabase.load()
        if (stored != null) {
            return@coroutineScope stored
        }
        val name = async { client.getName() }
        val friends = async { client.getFriends() }
        val profile = async { client.getProfile() }
        val details = UserDetails(
            name = name.await(),
            friends = friends.await(),
            profile = profile.await(),
        )
        backgroundScope.launch { userDatabase.save(details) }
        details
    }
}

interface UserDataClient {
    suspend fun getName(): String
    suspend fun getFriends(): List<Friend>
    suspend fun getProfile(): Profile
}

interface UserDetailsDatabase {
    suspend fun load(): UserDetails?
    suspend fun save(user: UserDetails)
}

data class UserDetails(
    val name: String,
    val friends: List<Friend>,
    val profile: Profile
)

data class Friend(val id: String)
data class Profile(val description: String)
