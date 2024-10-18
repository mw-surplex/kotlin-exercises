package functional.base

class Observable<T>(initial: T) {
    var value: T = initial
}

fun main() {
    //val observable = Observable(1)
    //println(observable.value) // 1
    //observable.observe { println("Changed to $it") }
    //observable.value = 2 // Changed to 2
    //println(observable.value) // 2
    //observable.observe { println("now it is $it") }
    //observable.value = 3
    //// Changed to 3
    //// now it is 3
}
