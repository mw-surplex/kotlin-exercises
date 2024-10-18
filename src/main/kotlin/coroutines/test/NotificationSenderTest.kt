package coroutines.test

import kotlinx.coroutines.*

class NotificationSender(
    private val client: NotificationClient,
    private val exceptionCollector: ExceptionCollector,
    dispatcher: CoroutineDispatcher,
) {
    private val exceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            exceptionCollector.collectException(throwable)
        }
    val scope: CoroutineScope = CoroutineScope(
        SupervisorJob() + dispatcher + exceptionHandler
    )

    fun sendNotifications(notifications: List<Notification>) {
        notifications.forEach { notification ->
            scope.launch {
                client.send(notification)
            }
        }
    }

    fun cancel() {
        scope.coroutineContext.cancelChildren()
    }
}

data class Notification(val id: String)

interface NotificationClient {
    suspend fun send(notification: Notification)
}

interface ExceptionCollector {
    fun collectException(throwable: Throwable)
}
