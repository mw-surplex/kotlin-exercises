package coroutines.flow

import kotlinx.coroutines.flow.*

val infiniteFlow: Flow<Unit> = TODO()

val neverFlow: Flow<Nothing> = TODO()

fun everyFlow(timeMillis: Long): Flow<Unit> = TODO()

fun <T> flowOf(lambda: suspend () -> T): Flow<T> = TODO()

fun <T> flowOfFlatten(
    lambda: suspend () -> Flow<T>
): Flow<T> = TODO()
