package coroutines.context

import kotlinx.coroutines.*
import org.junit.Test
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.*
import kotlin.test.assertEquals

@Suppress("FunctionName")
class CounterContextTests {
//    @Test
//    fun `should return next numbers in the same coroutine`() = runBlocking<Unit>(CounterContext()) {
//        assertEquals(0, coroutineContext[CounterContext]?.next())
//        assertEquals(1, coroutineContext[CounterContext]?.next())
//        assertEquals(2, coroutineContext[CounterContext]?.next())
//        assertEquals(3, coroutineContext[CounterContext]?.next())
//        assertEquals(4, coroutineContext[CounterContext]?.next())
//    }
//
//    @Test
//    fun `should have independent counter for each instance`() = runBlocking<Unit> {
//        launch(CounterContext()) {
//            assertEquals(0, coroutineContext[CounterContext]?.next())
//            assertEquals(1, coroutineContext[CounterContext]?.next())
//            assertEquals(2, coroutineContext[CounterContext]?.next())
//        }
//        launch(CounterContext()) {
//            assertEquals(0, coroutineContext[CounterContext]?.next())
//            assertEquals(1, coroutineContext[CounterContext]?.next())
//            assertEquals(2, coroutineContext[CounterContext]?.next())
//        }
//    }
//
//    @Test
//    fun `should propagate to the child`() = runBlocking<Unit>(CounterContext()) {
//        assertEquals(0, coroutineContext[CounterContext]?.next())
//        launch {
//            assertEquals(1, coroutineContext[CounterContext]?.next())
//            launch {
//                assertEquals(2, coroutineContext[CounterContext]?.next())
//            }
//            launch(CounterContext()) {
//                assertEquals(0, coroutineContext[CounterContext]?.next())
//                assertEquals(1, coroutineContext[CounterContext]?.next())
//                launch {
//                    assertEquals(2, coroutineContext[CounterContext]?.next())
//                }
//            }
//        }
//    }
}
