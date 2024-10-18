package functional.collections

// Powerset returns set of all subsets including full set and empty set
// https://en.wikipedia.org/wiki/Power_set
fun <T> Collection<T>.powerset(): Set<Set<T>> {
    TODO()
}

fun main() {
    println(setOf<Int>().powerset()) // [[]]
    println(setOf("A").powerset()) // [[], [A]]
    println(setOf('A', 'B').powerset()) // [[], [B], [A], [B, A]]
    println(setOf(1, 2, 3).powerset())
    // [[], [3], [2], [3, 2], [1], [3, 1], [2, 1], [3, 2, 1]]
    println(setOf(1, 2, 3, 4).powerset())
    // [[], [4], [3], [4, 3], [2], [4, 2], [3, 2], [4, 3, 2], [1], [4, 1], [3, 1], [4, 3, 1], [2, 1], [4, 2, 1], [3, 2, 1], [4, 3, 2, 1]]
}
