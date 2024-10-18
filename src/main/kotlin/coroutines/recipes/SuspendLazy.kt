package coroutines.recipes

fun <T> suspendLazy(initializer: suspend () -> T):SuspendLazy<T> =
    TODO()

interface SuspendLazy<T> : suspend () -> T {
    val isInitialized: Boolean
    fun valueOrNull(): T?
    override suspend operator fun invoke(): T
}
