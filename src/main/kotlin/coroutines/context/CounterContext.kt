package coroutines.context

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.coroutines.*

// TODO

//fun main(): Unit = runBlocking(CounterContext()) {
//    println(coroutineContext[CounterContext]?.next()) // 0
//    println(coroutineContext[CounterContext]?.next()) // 1
//    launch {
//        println(coroutineContext[CounterContext]?.next())// 2
//        println(coroutineContext[CounterContext]?.next())// 3
//    }
//    launch(CounterContext()) {
//        println(coroutineContext[CounterContext]?.next())// 0
//        println(coroutineContext[CounterContext]?.next())// 1
//    }
//}
