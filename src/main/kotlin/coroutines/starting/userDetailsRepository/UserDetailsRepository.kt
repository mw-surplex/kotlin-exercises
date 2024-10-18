package coroutines.starting.userDetailsRepository

import kotlinx.coroutines.*

class UserDetailsRepository(
    private val client: UserDataClient,
    private val userDatabase: UserDetailsDatabase,
    private val backgroundScope: CoroutineScope,
) {
    suspend fun getUserDetails(): UserDetails {
        TODO()
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
