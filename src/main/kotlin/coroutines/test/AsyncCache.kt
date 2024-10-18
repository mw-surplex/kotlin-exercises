//package coroutines.test
//
//import kotlinx.coroutines.*
//import kotlinx.coroutines.test.currentTime
//import kotlinx.coroutines.test.runTest
//import org.junit.After
//import org.junit.Assert.assertEquals
//import kotlin.test.Test
//
//class SuspendingCache<K : Any, V> {
//    private val cache = mutableMapOf<K, Deferred<V>>()
//    private val lock = Any()
//
//    suspend fun get(
//        key: K,
//        build: suspend (key: K) -> V
//    ): V = coroutineScope {
//        val deferred = synchronized(lock) {
//            val deferred = cache[key]
//            if (deferred != null && !deferred.isCancelled) {
//                deferred
//            } else {
//                val newDeferred = async {
//                    build(key)
//                }
//                cache[key] = newDeferred
//                newDeferred
//            }
//        }
//        try {
//            deferred.await()
//        } catch (_: Throwable) {
//            ensureActive()
//            get(key, build)
//        }
//    }
//
//    fun invalidateAll() = synchronized(lock) {
//        cache.forEach { (_, deferred) ->
//            deferred.cancel()
//        }
//        cache.clear()
//    }
//}
