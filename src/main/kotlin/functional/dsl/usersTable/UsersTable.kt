package functional.dsl.usersTable

import functional.dsl.table.TableBuilder
import functional.dsl.table.TdBuilder
import functional.dsl.table.TrBuilder

data class User(val id: String, val name: String, val points: Int, val category: String)

//fun userTable(users: List<User>): TableBuilder = table {
//    tr {
//        td { +"Id" }
//        td { +"Name" }
//        td { +"Points" }
//        td { +"Category" }
//    }
//    for (user in users) {
//        userRow(user)
//    }
//}

// TODO

fun main() {
    val users = listOf(
        User("1", "Randy", 2, "A"),
        User("4", "Andy", 4, "B"),
        User("3", "Mandy", 1, "C"),
        User("5", "Cindy", 5, "A"),
        User("2", "Lindy", 3, "B"),
    )
    // val table = userTable(users)
    // println(table)
    // <table>
    // <tr><td>Id</td><td>Name</td>
    // <td>Points</td><td>Category</td></tr>
    // <tr><td>1</td><td>Randy</td><td>2</td><td>A</td></tr>
    // <tr><td>4</td><td>Andy</td><td>4</td><td>B</td></tr>
    // <tr><td>3</td><td>Mandy</td><td>1</td><td>C</td></tr>
    // <tr><td>5</td><td>Cindy</td><td>5</td><td>A</td></tr>
    // <tr><td>2</td><td>Lindy</td><td>3</td><td>B</td></tr>
    // </table>
}

fun table(init: TableBuilder.() -> Unit): TableBuilder =
    TableBuilder().apply(init)

data class TableBuilder(
    var trs: List<TrBuilder> = emptyList()
) {
    fun tr(init: TrBuilder.() -> Unit) {
        trs += TrBuilder().apply(init)
    }

    override fun toString(): String =
        "<table>${trs.joinToString(separator = "")}</table>"
}

data class TrBuilder(
    var tds: List<TdBuilder> = emptyList()
) {
    fun td(init: TdBuilder.() -> Unit) {
        tds += TdBuilder().apply(init)
    }

    override fun toString(): String =
        "<tr>${tds.joinToString(separator = "")}</tr>"
}

data class TdBuilder(var text: String = "") {
    operator fun String.unaryPlus() {
        text += this
    }

    override fun toString(): String = "<td>$text</td>"
}
