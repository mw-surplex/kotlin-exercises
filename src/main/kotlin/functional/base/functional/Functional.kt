package functional.base.functional

class FunctionsClassic {

    fun add(num1: Int, num2: Int): Int = num1 + num2

    fun printNum(num: Int) {
        print(num)
    }

    fun triple(num: Int): Int = num * 3

    fun produceName(name: String): Name = Name(name)

    fun longestOf(
        str1: String,
        str2: String,
        str3: String,
    ): String =
        maxOf(str1, str2, str3, compareBy { it.length })
}

data class Name(val name: String)

class AnonymousFunctionalTypeSpecified {
    val add: (Int, Int) -> Int = fun(num1, num2) = num1 + num2

    // TODO: Implement printNum, triple, produceName and longestOf properties using anonymous functions
    //  their type should be specified explicitly
    //  See add property for example
}

class AnonymousFunctionalTypeInferred {
    val add = fun(num1: Int, num2: Int) = num1 + num2

    // TODO: Implement printNum, triple, produceName and longestOf properties using anonymous functions
    //  their type should be inferred by compiler
    //  See add property for example
}

class LambdaFunctionalTypeSpecified {
    val add: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }

    // TODO: Implement printNum, triple, produceName and longestOf properties using lambda functions
    //  their type should be specified explicitly
    //  See add property for example
}

class LambdaFunctionalTypeInferred {
    val add = { num1: Int, num2: Int -> num1 + num2 }

    // TODO: Implement printNum, triple, produceName and longestOf properties using lambda functions
    //  their type should be inferred by compiler
    //  See add property for example
}

class LambdaUsingImplicitParameter {
    val add: (Int, Int) -> Int = { num1, num2 -> num1 + num2 }

    // TODO: Implement printNum, triple, produceName and longestOf properties,
    //  just like in LambdaFunctionalTypeSpecified, but this time, whenever possible,
    //  use implicit parameter `it`.
}
