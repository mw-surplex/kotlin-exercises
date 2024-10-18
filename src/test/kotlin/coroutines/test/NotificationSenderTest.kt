package coroutines.test

import kotlinx.coroutines.*
import org.junit.Test

class NotificationSenderTest {

    @Test
    fun `should send notifications concurrently`() {
        // TODO
    }

    @Test
    fun `should cancel all coroutines when cancel is called`() {
        // TODO
    }

    @Test
    fun `should not cancel other sending processes when one of them fails`() {
        // TODO
    }

    @Test
    fun `should collect exceptions from all coroutines`() {
        // TODO
    }
}

class FakeNotificationClient(
    val delayTime: Long = 0L,
    val failEvery: Int = Int.MAX_VALUE
) : NotificationClient {
    var sent = emptyList<Notification>()
    var counter = 0
    var usedThreads = emptyList<String>()

    override suspend fun send(notification: Notification) {
        if (delayTime > 0) delay(delayTime)
        usedThreads += Thread.currentThread().name
        counter++
        if (counter % failEvery == 0) {
            throw FakeFailure(notification)
        }
        sent += notification
    }
}

class FakeFailure(val notification: Notification) : Throwable("Planned fail for notification ${notification.id}")

class FakeExceptionCollector : ExceptionCollector {
    var collected = emptyList<Throwable>()

    override fun collectException(throwable: Throwable) = synchronized(this) {
        collected += throwable
    }
}
