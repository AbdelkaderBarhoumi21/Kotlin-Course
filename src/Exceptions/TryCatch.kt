package Exceptions

fun main(args: Array<String>) {
    // Classic try-catch

    try {
        val result = 10 / 0
    } catch (e: ArithmeticException) {
        println("Division by zero:${e.message}")
    } finally {
        println("Done")
    }

    val numbers = try {
        "abc".toInt()

    } catch (e: NumberFormatException) {
        0
    }
    println(numbers)
}