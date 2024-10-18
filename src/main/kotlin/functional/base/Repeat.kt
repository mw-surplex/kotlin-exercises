package functional.base

fun repeat(times: Int, action: () -> Unit) {

}

fun main(args: Array<String>) {
    repeat(5) { print("A") } // AAAAA
}
