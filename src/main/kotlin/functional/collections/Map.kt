package functional.collections

import kotlin.collections.onEach
// To prevent unintentional stdlib functions usage
import kotlin.collections.map as `implement it yourself`
import kotlin.collections.onEach as `implement it yourself`
import kotlin.collections.flatMap as `implement it yourself`
import kotlin.collections.filter as `implement it yourself`

inline fun <T, C: Iterable<T>> C.onEach(operation: (T) -> Unit): C {
    for (elem in this) {
        operation(elem)
    }
    return this
}

inline fun <T, R> Iterable<T>.flatMap(transformation: (T) -> Iterable<R>): List<R> {
    val list = ArrayList<R>()
    for (elem in this) {
        list.addAll(transformation(elem))
    }
    return list
}

inline fun <T> Iterable<T>.filter(predicate: (T) -> Boolean): List<T> {
    val list = ArrayList<T>()
    for (elem in this) {
        if (predicate(elem)) {
            list.add(elem)
        }
    }
    return list
}

// TODO

fun main() {
    val numbers = 1..10
    val names = listOf("Mike", "Jane", "Marcin", "John", "James")

    numbers.onEach { print(it) } // 12345678910
    println()
    names.onEach { print(it) } // MikeJaneMarcinJohnJames
    println()

    println(names.filter { it.startsWith("J") }) // [Jane, John, James]
    println(names.filter { it.startsWith("M") }) // [Mike, Marcin]

    println(names.flatMap { it.toList() }) // [M, i, k, e, J, a, n, e, M, a, r, c, i, n, J, o, h, n, J, a, m, e, s]
    println(numbers.flatMap { listOf(it, it + 10) }) // [1, 11, 2, 12, 3, 13, 4, 14, 5, 15, 6, 16, 7, 17, 8, 18, 9, 19, 10, 20]

//    println(names.map { it.toUpperCase() }) // [MIKE, JANE, MARCIN, JOHN, JAMES]
//    println(numbers.map { it * 10 }) // [10, 20, 30, 40, 50, 60, 70, 80, 90, 100]

    //val list = listOf(1, 2, 3)
    //println(list.map { it * 2 }) // [2, 4, 6]
    //println(list.map { "$it!" }) // [1!, 2!, 3!]
    //println(list.map { it % 2 == 0 }) // [false, true, false]
}
