package effective.safe.chunked

import kotlinx.coroutines.flow.*
import kotlin.time.Duration

fun <T> Flow<T>.chunked(
    duration: Duration
): Flow<List<T>> = TODO()
