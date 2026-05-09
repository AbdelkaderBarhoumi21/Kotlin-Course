package OOP

enum class Operation(val symbol: String) {
    PLUS("+") {
        override fun apply(a: Int, b: Int) = a + b
        override fun describe(a: Int, b: Int) =
            "$a + $b = ${apply(a, b)}"
    },

    MINUS("-") {
        override fun apply(a: Int, b: Int) = a - b
        override fun describe(a: Int, b: Int) =
            "$a - $b = ${apply(a, b)}"
    },
    TIMES("×") {
        override fun apply(a: Int, b: Int) = a * b
        override fun describe(a: Int, b: Int) =
            "$a × $b = ${apply(a, b)}"
    },

    DIVIDE("/") {
        override fun apply(a: Int, b: Int): Int {
            if (b == 0) throw IllegalArgumentException("Cannot divide by zero!")
            return a / b
        }

        override fun describe(a: Int, b: Int) =
            "$a / $b = ${apply(a, b)}"
    };

    abstract fun apply(a: Int, b: Int): Int
    abstract fun describe(a: Int, b: Int): String
}


fun main(args: Array<String>) {
    // ── Direct usage ──────────────────────────────────
    println(Operation.PLUS.apply(10, 5))     // 15
    println(Operation.MINUS.apply(10, 5))    // 5
    println(Operation.TIMES.apply(10, 5))    // 50
    println(Operation.DIVIDE.apply(10, 5))   // 2

    // ── describe() ───────────────────────────────────
    println(Operation.PLUS.describe(10, 5))   // 10 + 5 = 15
    println(Operation.MINUS.describe(10, 5))  // 10 - 5 = 5
    println(Operation.TIMES.describe(10, 5))  // 10 × 5 = 50
    println(Operation.DIVIDE.describe(10, 5)) // 10 / 5 = 2
    // ── iterate all operations ────────────────────────
    println("\n--- All Operations on 10 and 3 ---")

    Operation.entries.forEach { op ->
        println(op.describe(10, 3))
    }
    // ── symbol property ──────────────────────────────
    println("\n--- Symbols ---")
    Operation.entries.forEach { op -> println("${op.name.padEnd(10)} symbol = ${op.symbol}") }

    // ── get from String ──────────────────────────────
    val op = Operation.valueOf("PLUS")
    println("Get enum from String: $op")

    // ── divide by zero ────────────────────────────────
    try {
        Operation.DIVIDE.apply(10, 0)

    } catch (e: IllegalArgumentException) {
        println("Error: ${e.message}")
    }


}