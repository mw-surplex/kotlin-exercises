package coroutines.suspension

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

var continuation: Continuation<String>? = null

suspend fun continuationSteal(console: Console) {
    console.println("Before")
    // TODO
    console.println("After")
}

interface Console {
    fun println(text: Any?)
}

fun main(): Unit = runBlocking<Unit> {
    launch {
        continuationSteal(object : Console {
            override fun println(text: Any?) {
                kotlin.io.println(text)
            }
        })
    }
    delay(1000)
    continuation?.resume("This is some text")
}
// Before
// (1 sec)
// This is some text
// After
