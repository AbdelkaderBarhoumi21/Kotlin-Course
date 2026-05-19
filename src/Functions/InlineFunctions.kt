package Functions
//Lambdas in Kotlin compile to objects, which has a small overhead.
// For high-frequency higher-order functions, mark them `inline` — the compiler will inline the lambda body at each call site:


inline fun repeat(n: Int, block: () -> Unit) {
    for (i in 0 until n) block()
}

fun doSomething() {
    println("Hello World!")
}

fun main(args: Array<String>) {
    repeat(2) { doSomething() }

}