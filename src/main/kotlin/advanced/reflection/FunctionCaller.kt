package advanced.reflection.functioncaller

import kotlin.reflect.KFunction
import kotlin.reflect.KType
import kotlin.reflect.typeOf

class FunctionCaller {
    inline fun <reified T> setConstant(value: T) {
        setConstant(typeOf<T>(), value)
    }

    fun setConstant(type: KType, value: Any?) {
        TODO()
    }

    fun <T> call(function: KFunction<T>): T {
        TODO()
    }
}

fun printStrIntNum(str: String, int: Int, num: Number) {
    println("str: $str, int: $int, num: $num")
}

fun printWithOptionals(l: Long = 999, s: String) {
    println("l: $l, s: $s")
}

fun main() {
    val caller = FunctionCaller()
    caller.setConstant("ABC")
    caller.setConstant(123)
    caller.setConstant(typeOf<Number>(), 3.14)
    caller.call(::printStrIntNum)
    // str: ABC, int: 123, num: 3.14
    caller.call(::printWithOptionals)
    // l: 999, s: ABC
}
