package coroutines.flow.allusers

import coroutines.flow.AllUsers
import coroutines.flow.User
import coroutines.flow.UserRepository
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

internal class AllUsersTests {

    @Test
    fun test() = runTest {
        val size = 10_000
        val pageSize = 10
        val repo = object : UserRepository {
            val users = List(size) { User("User$it") }
            var timesUsed = 0

            override fun fetchUsers(pageNumber: Int): List<User> =
                users.drop(pageSize * pageNumber)
                    .take(pageSize)
                    .also { timesUsed++ }
        }
        val service = AllUsers(repo)
        val s = service.getAllUsers()
        assertEquals(size, s.count())
        assertEquals(size / pageSize + 1, repo.timesUsed)
    }
}
