package operators

// Dart's call() = Kotlin's invoke(). Exact same concept, different name.
// Lambda — no state, fixed behavior
val addFunction = { x: Int -> x + 10 }

// Class with invoke() — has state, configurable behavior
class Adder(private val amount: Int) {
    operator fun invoke(x: Int): Int = x + amount
}

// You can even change the state later
class Counter {
    private var count = 0
    operator fun invoke(): Int = ++count
}


fun main() {
    val add10 = Adder(10)
    val add100 = Adder(100)

    println(add10(5))
    println(add100(5))

    val counter = Counter()
    println(counter())  // 1
    println(counter())  // 2
    println(counter())  // 3
    // a lambda could never do this
}