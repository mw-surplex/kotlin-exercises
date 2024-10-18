package coroutines.scope.baseViewModel

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@Suppress("FunctionName")
class BaseViewModelTests {

    private val UI = newSingleThreadContext("UIThread") // Normally it will be Dispatchers.Main

    @Before
    fun setUp() {
        Dispatchers.setMain(UI)
    }

    @Test
    fun `onDestroy cancels all jobs`() = runTest {
        var jobs = listOf<Job>()
        val viewModel = object : BaseViewModel() {
            init {
                jobs += scope.launch {
                    delay(Long.MAX_VALUE)
                }
                jobs += scope.launch {
                    delay(Long.MAX_VALUE)
                }
            }
        }
        delay(200)
        viewModel.onCleared()
        delay(200)
        assertEquals(listOf(true, true), jobs.map { it.isCancelled })
    }

    @Test
    fun `Coroutines run on main thread`() = runTest {
        var threads = listOf<Thread>()
        val viewModel = object : BaseViewModel() {
            init {
                scope.launch {
                    threads += Thread.currentThread()
                }
            }
        }
        delay(100)
        viewModel.onCleared()
        delay(100)
        threads.forEach {
            assert(it.name.startsWith("UIThread")) { "We should switch to UI thread, and now we are on ${it.name}" }
        }
        assert(threads.isNotEmpty())
    }

    @Test
    fun `When a job throws an error, it is handled`(): Unit = runTest {
        val error1 = Error()
        val error2 = Error()
        val vm = object : BaseViewModel() {
            init {
                scope.launch {
                    throw error1
                }
                scope.launch {
                    throw error2
                }
            }
        }
        var exceptions = setOf<Throwable>()
        vm.exceptions.onEach { exceptions += it }.launchIn(backgroundScope)
        delay(200)
        assertEquals(setOf(error1, error2), exceptions)
    }

    class FakeViewModelForSingleExceptionHandling(val onSecondAction: () -> Unit) : BaseViewModel() {
        fun onCreate() {
            scope.launch {
                delay(100)
                throw Error()
            }
            scope.launch {
                delay(200)
                onSecondAction()
            }
        }
    }

    @Test
    fun `Error on a single coroutine, does not cancel others`() = runBlocking {
        var called = false
        var started = false
        object : BaseViewModel() {
            init {
                scope.launch {
                    delay(100)
                    throw Error()
                }
                scope.launch {
                    started = true
                    delay(200)
                    called = true
                }
            }
        }
        delay(300)
        assertTrue(started)
        assertTrue(called)
    }
}
