package coroutines.starting

import coroutines.starting.userDetailsRepository.Friend
import coroutines.starting.userDetailsRepository.Profile
import kotlinx.coroutines.*

class ShowUserDataUseCase(
    private val repo: UserDataRepository,
    private val view: UserDataView,
    private val notificationScope: CoroutineScope,
) {
    suspend fun show() {}
}

interface UserDataRepository {
    suspend fun notifyProfileShown()
    suspend fun getName(): String
    suspend fun getFriends(): List<Friend>
    suspend fun getProfile(): Profile
}

interface UserDataView {
    fun show(user: User)
}

data class User(
    val name: String,
    val friends: List<Friend>,
    val profile: Profile
)
data class Friend(val id: String)
data class Profile(val description: String)

class TestUserDataRepository : UserDataRepository {
    override suspend fun notifyProfileShown() {
        delay(10000)
    }

    override suspend fun getName(): String {
        delay(1000)
        return "Ben"
    }

    override suspend fun getFriends(): List<Friend> {
        delay(1000)
        return listOf(Friend("friend-id-1"))
    }

    override suspend fun getProfile(): Profile {
        delay(1000)
        return Profile("Example description")
    }
}

class TestUserDataView : UserDataView {
    override fun show(user: User) {
        print(user)
    }
}
