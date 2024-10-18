package coroutines.recipes

import kotlinx.coroutines.*

suspend fun <T> raceOf(
    racer: suspend CoroutineScope.() -> T,
    vararg racers: suspend CoroutineScope.() -> T
): T = TODO()
