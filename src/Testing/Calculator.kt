package Testing

class Calculator {
    fun add(a: Int, b: Int): Int = a + b
    fun subtract(a: Int, b: Int): Int = a - b
    fun divide(a: Int, b: Int): Int {
        if (b == 0) throw ArithmeticException("Cannot divide by zero")
        return a / b
    }

    fun isEven(n: Int): Boolean = n % 2 == 0
}

fun main(args: Array<String>) {}