package coroutines.test

import kotlinx.coroutines.test.runTest
import org.junit.Test

@Suppress("FunctionName")
class UserDetailsRepositoryTest {

    @Test
    fun `should fetch details asynchronously`() = runTest {
        // given
        val client = object : UserDataClient {
            override suspend fun getName(): String {
                // TODO
                return "Ben"
            }
    
            override suspend fun getFriends(): List<Friend> {
                // TODO
                return listOf(Friend("friend-id-1"))
            }
    
            override suspend fun getProfile(): Profile {
                // TODO
                return Profile("Example description")
            }
        }
        var savedDetails: UserDetails? = null
        val database = object : UserDetailsDatabase {
            override suspend fun load(): UserDetails? {
                // TODO
                return savedDetails
            }
    
            override suspend fun save(user: UserDetails) {
                // TODO
                savedDetails = user
            }
        }
    
        val repo: UserDetailsRepository = TODO()
    
        // when
        val details = repo.getUserDetails()
    
        // then data are fetched asynchronously
        // TODO
    
        // when all children are finished
        // TODO
    
        // then data are saved to the database
        // TODO
    
        // when getting details again
        // TODO
    
        // then data are loaded from the database
        // TODO
    }
    
    
}
