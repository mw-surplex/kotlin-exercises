package examples.mastery

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test
import kotlin.test.assertEquals

class UserRepositoryTest {
    @Test
    fun shouldNotLeakAccessToInternalList() {
        val userRepository = UserRepository()
        val storedUsers = userRepository.loadAll()
        userRepository.add(1, "ABC")
        assertEquals(emptyMap(), storedUsers)
    }

    @Test
    fun shouldAllowConcurrentAccess() = runBlocking(Dispatchers.IO) {
        val userRepository = UserRepository()
        val usersNum = 10_000
        coroutineScope {
            repeat(usersNum) {
                launch { userRepository.add(it, "User$it") }
                if (it % 100 == 0) {
                    launch {
                        val all = userRepository.loadAll()
                        all.forEach { (id, name) -> assertEquals("User$id", name) }
                    }
                }   
            }
        }
        assertEquals(usersNum, userRepository.loadAll().size)
    }
}
