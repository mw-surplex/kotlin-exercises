package coroutines.flow

import kotlinx.coroutines.flow.Flow

class AllUsers(private val repository: UserRepository) {
    fun getAllUsers(): Flow<User> = TODO()
}

interface UserRepository {
    fun fetchUsers(pageNumber: Int): List<User>
}

data class User(val name: String)
