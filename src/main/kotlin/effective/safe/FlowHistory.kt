package effective.safe

import kotlinx.coroutines.flow.*

fun <T> Flow<T>.withHistory(): Flow<List<T>> = flow {
    val history = mutableListOf<T>()
    emit(history)
    collect {
        history += it
        emit(history)
    }
}

suspend fun main() {
    flowOf(1, 2, 3)
        .withHistory()
        .toList()
        .let(::println)
    // [[1, 2, 3], [1, 2, 3], [1, 2, 3], [1, 2, 3]]
}
