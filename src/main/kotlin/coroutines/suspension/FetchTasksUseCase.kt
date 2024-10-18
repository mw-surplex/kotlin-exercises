package coroutines.suspension

class FetchTasksUseCase(
    private val callbackUseCase: FetchTasksCallbackUseCase
) {
    @Throws(ApiException::class)
    suspend fun fetchTasks(): List<Task> =
        TODO()
    suspend fun fetchTasksResult(): Result<List<Task>> =
        TODO()
    suspend fun fetchTasksOrNull(): List<Task>? =
        TODO()
}

interface FetchTasksCallbackUseCase {
    fun fetchTasks(
        onSuccess: (List<Task>) -> Unit,
        onError: (Throwable) -> Unit
    ): Cancellable
}

fun interface Cancellable {
    fun cancel()
}

data class Task(val name: String, val priority: Int)

class ApiException(val code: Int, message: String): Throwable(message)
