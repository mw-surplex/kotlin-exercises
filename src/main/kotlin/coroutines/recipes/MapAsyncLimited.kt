package coroutines.recipes

suspend fun <T, R> Iterable<T>.mapAsync(
    concurrency: Int,
    transformation: suspend (T) -> R
): List<R> = TODO()

