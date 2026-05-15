package Functions


// Function that takes a function as a parameter
fun <T> List<T>.filterAndTransform(
    predicate: (T) -> Boolean,
    transform: (T) -> String
): List<String> {
    return this.filter(predicate).map(transform)
}

// Function that returns a function
fun multiplyBy(factor: Int): (Int) -> Int {
    return { number -> number * factor }
}


fun main(args: Array<String>) {

    val names = listOf("Alice", "Bob", "Charlie", "Anna")
    val result = names.filterAndTransform(
        predicate = { name -> name.startsWith("A") },
        transform = { name -> name.uppercase() }
    )
    println("Names result: $result")

    // Function that returns a function
    val multiplication = multiplyBy(3)
    val mResult = multiplication(5)
    println("Multiplication result: $mResult")

    // Function composition
    val addOne: (Int) -> Int = { it + 1 }
    val doubler: (Int) -> Int = { it * 2 }
    val combined: (Int) -> Int = { doubler(addOne(it)) }
    val cResult = combined(5)
    println("Combined result: $cResult")

    // Practical example: a processing pipeline
    fun <T> pipeline(vararg operations: (T) -> T): (T) -> T = { input ->
        operations.fold(input) { acc, operation -> operation(acc) }
    }

    val processText = pipeline<String>({
        it.trim()
    }, { it.lowercase() }, { it.replace(" ", "-") })

    val pResult = processText(" Hello World ")
    println(pResult)  // "hello-world"


}