package functional.dsl.table

//fun createTable(): TableBuilder = table {
//    tr {
//        td { +"A" }
//        td { +"B" }
//    }
//    tr {
//        td { +"C" }
//        td { +"D" }
//    }
//}

data class TableBuilder(
    var trs: List<TrBuilder> = emptyList()
) {
    override fun toString(): String =
        "<table>${trs.joinToString(separator = "")}</table>"
}
data class TrBuilder(
    var tds: List<TdBuilder> = emptyList()
) {
    override fun toString(): String =
        "<tr>${tds.joinToString(separator = "")}</tr>"
}
data class TdBuilder(
    var text: String = ""
) {
    override fun toString(): String = "<td>$text</td>"
}

fun main() {
    // println(createTable()) //<table><tr><td>This is row 1</td><td>This is row 2</td></tr></table>
}
