package coroutines.flow

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.currentTime

fun <T> Flow<T>.withVirtualTime(testScope: TestScope): Flow<ValueAndTime<T>> =
    map { ValueAndTime(it, testScope.currentTime) }

data class ValueAndTime<T>(val value: T, val timeMillis: Long)
