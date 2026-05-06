package operators

fun main() {

    val a = 10
    val b = 3

    // ─── Arithmetic ───────────────────────────────────────
    val sum = a + b
    val difference = a - b
    val product = a * b
    val quotient = a / b        // integer division (no decimals)
    val remainder = a % b

    println("=== Arithmetic ===")
    println("$a + $b = $sum")           // 13
    println("$a - $b = $difference")    // 7
    println("$a * $b = $product")       // 30
    println("$a / $b = $quotient")      // 3  (integer division)
    println("$a % $b = $remainder")     // 1

    // ─── Decimal Division ─────────────────────────────────
    val decimalDivision = a.toDouble() / b

    println("\n=== Decimal Division ===")
    println("$a / $b = $decimalDivision")   // 3.3333333333333335

    // ─── Comparison ───────────────────────────────────────
    println("\n=== Comparison ===")
    println("$a == $b  → ${a == b}")    // false
    println("$a != $b  → ${a != b}")    // true
    println("$a >  $b  → ${a > b}")     // true
    println("$a <  $b  → ${a < b}")     // false
    println("$a >= $b  → ${a >= b}")    // true
    println("$a <= $b  → ${a <= b}")    // false

    // ─── Structural vs Referential Equality ───────────────
    val s1 = "Hello"
    val s2 = "Hello"

    println("\n=== Structural vs Referential Equality ===")
    println("s1 == s2  → ${s1 == s2}")    // true  — same content  (like equals() in Java)
    println("s1 === s2 → ${s1 === s2}")   // true  — same reference (JVM optimizes string literals)

    // extra — show the difference clearly
    val s3 = String("Hello".toCharArray())   // force new object in memory
    println("\ns1 == s3  → ${s1 == s3}")     // true  — same content
    println("s1 === s3 → ${s1 === s3}")      // false — different reference in memory
}