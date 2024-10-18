package functional.base.functional

import org.junit.Test
import kotlin.reflect.KClass
import kotlin.reflect.typeOf
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class FunctionalTest {

    @Test
    fun `AnonymousFunctionalTypeSpecified has correct property signatures`() {
        checkPropertySignatures(AnonymousFunctionalTypeSpecified::class)
    }

    @Test
    fun `AnonymousFunctionalTypeSpecified has correct property behavior`() {
        checkPropertyBehavior(AnonymousFunctionalTypeSpecified())
    }

    @Test
    fun `AnonymousFunctionalTypeInferred has correct property signatures`() {
        checkPropertySignatures(AnonymousFunctionalTypeInferred::class)
    }

    @Test
    fun `AnonymousFunctionalTypeInferred has correct property behavior`() {
        checkPropertyBehavior(AnonymousFunctionalTypeInferred())
    }

    @Test
    fun `LambdaFunctionalTypeSpecified has correct property signatures`() {
        checkPropertySignatures(LambdaFunctionalTypeSpecified::class)
    }

    @Test
    fun `LambdaFunctionalTypeSpecified has correct property behavior`() {
        checkPropertyBehavior(LambdaFunctionalTypeSpecified())
    }

    @Test
    fun `LambdaFunctionalTypeInferred has correct property signatures`() {
        checkPropertySignatures(LambdaFunctionalTypeInferred::class)
    }

    @Test
    fun `LambdaFunctionalTypeInferred has correct property behavior`() {
        checkPropertyBehavior(LambdaFunctionalTypeInferred())
    }

    @Test
    fun `LambdaUsingImplicitParameter has correct property signatures`() {
        checkPropertySignatures(LambdaUsingImplicitParameter::class)
    }

    @Test
    fun `LambdaUsingImplicitParameter has correct property behavior`() {
        checkPropertyBehavior(LambdaUsingImplicitParameter())
    }

    private fun checkPropertySignatures(
        classToCheck: KClass<*>,
        expectLongestOf: Boolean = true,
    ) {
        val c = classToCheck.members
        val properties = mutableMapOf(
            "add" to typeOf<(Int, Int) -> Int>(),
            "printNum" to typeOf<(Int) -> Unit>(),
            "triple" to typeOf<(Int) -> Int>(),
            "produceName" to typeOf<(String) -> Name>(),
        )
        if (expectLongestOf) {
            properties += "longestOf" to typeOf<(String, String, String) -> String>()
        }
        for ((propertyName, propertyType) in properties) {
            val propertyReference = c.find { it.name == propertyName }
            assertNotNull(propertyReference) { "Property $propertyName is missing" }
            assertEquals(propertyType, propertyReference.returnType, "Property $propertyName has wrong type")
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T: Any> checkPropertyBehavior(
        instance: T,
        expectLongestOf: Boolean = true,
    ) {
        val members = instance::class.members
        val add = members.find { it.name == "add" }!!
        assertEquals(3, (add.call(instance) as (Int, Int) -> Int)(1, 2))
        assertEquals(12, (add.call(instance) as (Int, Int) -> Int)(4, 8))
        val printNum = members.find { it.name == "printNum" }!!
        (printNum.call(instance) as (Int) -> Unit)(42)
        val triple = members.find { it.name == "triple" }!!
        assertEquals(9, (triple.call(instance) as (Int) -> Int)(3))
        assertEquals(15, (triple.call(instance) as (Int) -> Int)(5))
        val produceName = members.find { it.name == "produceName" }!!
        assertEquals(Name("John"), (produceName.call(instance) as (String) -> Name)("John"))
        assertEquals(Name("Jane"), (produceName.call(instance) as (String) -> Name)("Jane"))
        if (expectLongestOf) {
            val longestOf = members.find { it.name == "longestOf" }!!
            assertEquals("abc", (longestOf.call(instance) as (String, String, String) -> String)("a", "ab", "abc"))
            assertEquals("xyz", (longestOf.call(instance) as (String, String, String) -> String)("x", "xy", "xyz"))
        }
    }
}
