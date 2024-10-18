package advanced.reflection.xmlserializer

import kotlin.reflect.KClass

fun serializeToXml(value: Any): String = TODO()

fun main() {
    data class SampleDataClass(
        val externalTxnId: String,
        val merchantTxnId: String,
        val reference: String
    )

    val data = SampleDataClass(
        externalTxnId = "07026984141550752666",
        merchantTxnId = "07026984141550752666",
        reference = "MERCHPAY"
    )

    println(serializeToXml(data))
    // <SampleDataClass>
    //     <externalTxnId>07026984141550752666</externalTxnId>
    //     <merchantTxnId>07026984141550752666</merchantTxnId>
    //     <reference>MERCHPAY</reference>
    // </SampleDataClass>

    @SerializationNameMapper(UpperSnakeCaseName::class)
    @SerializationIgnoreNulls
    class Book(
        val title: String,
        val author: String,
        @SerializationName("YEAR")
        val publicationYear: Int,
        val isbn: String?,
        @SerializationIgnore
        val price: Double,
    )

    @SerializationNameMapper(UpperSnakeCaseName::class)
    class Library(
        val catalog: List<Book>
    )

    val library = Library(
        catalog = listOf(
            Book(
                title = "The Hobbit",
                author = "J. R. R. Tolkien",
                publicationYear = 1937,
                isbn = "978-0-261-10235-4",
                price = 9.99,
            ),
            Book(
                title = "The Witcher",
                author = "Andrzej Sapkowski",
                publicationYear = 1993,
                isbn = "978-0-575-09404-2",
                price = 7.99,
            ),
            Book(
                title = "Antifragile",
                author = "Nassim Nicholas Taleb",
                publicationYear = 2012,
                isbn = null,
                price = 12.99,
            )
        )
    )

    println(serializeToXml(library))
    // <LIBRARY>
    //     <CATALOG>
    //         <BOOK>
    //             <AUTHOR>J. R. R. Tolkien</AUTHOR>
    //             <ISBN>978-0-261-10235-4</ISBN>
    //             <YEAR>1937</YEAR>
    //             <TITLE>The Hobbit</TITLE>
    //         </BOOK>
    //         <BOOK>
    //             <AUTHOR>Andrzej Sapkowski</AUTHOR>
    //             <ISBN>978-0-575-09404-2</ISBN>
    //             <YEAR>1993</YEAR>
    //             <TITLE>The Witcher</TITLE>
    //         </BOOK>
    //        <BOOK>
    //            <AUTHOR>Nassim Nicholas Taleb</AUTHOR>
    //            <YEAR>2012</YEAR>
    //            <TITLE>Antifragile</TITLE>
    //        </BOOK>
    //     </CATALOG>
    // </LIBRARY>
}

@Target(AnnotationTarget.PROPERTY)
annotation class SerializationName(val name: String)

@Target(AnnotationTarget.PROPERTY)
annotation class SerializationIgnore

@Target(AnnotationTarget.PROPERTY, AnnotationTarget.CLASS)
annotation class SerializationNameMapper(val mapper: KClass<out NameMapper>)

@Target(AnnotationTarget.CLASS)
annotation class SerializationIgnoreNulls

interface NameMapper {
    fun map(name: String): String
}

object LowerCaseName : NameMapper {
    override fun map(name: String): String = name.lowercase()
}

class SnakeCaseName : NameMapper {
    val pattern = "(?<=.)[A-Z]".toRegex()

    override fun map(name: String): String =
        name.replace(pattern, "_$0").lowercase()
}
object UpperSnakeCaseName : NameMapper {
    val pattern = "(?<=.)[A-Z]".toRegex()

    override fun map(name: String): String =
        name.replace(pattern, "_$0").uppercase()
}
