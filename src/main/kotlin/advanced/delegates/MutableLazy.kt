package advanced.delegates.mutablelazy

import kotlin.properties.ReadWriteProperty

fun <T> mutableLazy(
    initializer: () -> T
): ReadWriteProperty<Any?, T> = TODO()
