package effective.safe

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertEquals

class UserDownloaderTest {
    @Test
    fun test() = runBlocking {
        val downloader = UserDownloader(FakeNetworkService())
        coroutineScope {
            repeat(1_000_000) {
                launch(Dispatchers.Default) {
                    downloader.getUser(it)
                }
            }
        }
        assertEquals(1_000_000, downloader.downloaded().size)
    }
}
