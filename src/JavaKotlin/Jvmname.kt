package JavaKotlin

// NOTE: @file:JvmName must be on the FIRST line before package
// We demonstrate the concept here since we can't split into files easily

// ── What problem does @JvmName solve ? ───────────────────────────
//
// When you write top-level functions in a file "StringUtils.kt",
// Kotlin compiles them into a Java class called "StringUtilsKt"
// (notice the "Kt" suffix — ugly for Java callers)
//
// @file:JvmName("StringUtils") renames it to "StringUtils"
// so Java can call StringUtils.wordCount() instead of StringUtilsKt.wordCount()

// ── Simulating the concept with extension functions ───────────────

// These are top-level functions — in a real project in a separate file
// with @file:JvmName("StringUtils") at the top

fun String.wordCount(): Int = trim().split("\\s+".toRegex()).size

fun String.capitalize2(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { it.uppercase() }
    }

fun String.isPalindrome(): Boolean {
    val clean = lowercase().filter { it.isLetter() }
    return clean == clean.reversed()
}

fun String.countVowels(): Int =
    count { it.lowercaseChar() in "aeiou" }

fun String.truncate(maxLength: Int, suffix: String = "..."): String =
    if (length <= maxLength) this
    else take(maxLength - suffix.length) + suffix

// ── @JvmName on individual functions ─────────────────────────────
// Used when two Kotlin functions would generate the same JVM signature

// For example, these two functions differ only in their type parameter
// which is erased at runtime — they'd clash in Java without @JvmName

fun List<String>.printAll() {
    forEach { println("  str: $it") }
}


@JvmName("printAllInts")   // Java sees a different name — no clash
fun List<Int>.printAll() {
    forEach { println("  int: $it") }
}

// ── Practical example : number utilities ─────────────────────────
fun Int.toBinaryString(): String = Integer.toBinaryString(this)
fun Int.toHexString(): String = Integer.toHexString(this)
fun Int.isEven(): Boolean = this % 2 == 0
fun Int.isOdd(): Boolean = this % 2 != 0
fun Int.clamp(min: Int, max: Int) = maxOf(min, minOf(max, this))

fun main() {

    // ── 1. String extension functions ────────────────────────────
    println("=== String utilities ===")

    val sentence = "  hello world from kotlin  "
    println("  original    : '$sentence'")
    println("  wordCount   : ${sentence.wordCount()}")
    println("  capitalize  : '${sentence.trim().capitalize2()}'")

    val palindrome = "A man a plan a canal Panama"
    println("\n  '$palindrome'")
    println("  isPalindrome : ${palindrome.isPalindrome()}")

    val text = "Hello beautiful world"
    println("\n  '$text'")
    println("  countVowels : ${text.countVowels()}")
    println("  truncate 10 : '${text.truncate(10)}'")
    println("  truncate 15 : '${text.truncate(15)}'")
    println("  truncate 30 : '${text.truncate(30)}'")

    // ── 2. @JvmName on overloaded functions ──────────────────────
    println("\n=== @JvmName on printAll ===")

    val strings = listOf("apple", "banana", "cherry")
    val numbers = listOf(1, 2, 3, 4, 5)

    print("  strings : ")
    strings.printAll()

    print("  numbers : ")
    numbers.printAll()   // Kotlin calls the right one by type inference
    // Java would call printAllInts() explicitly thanks to @JvmName

    // ── 3. Int extension utilities ───────────────────────────────
    println("\n=== Int utilities ===")

    val n = 255
    println("  $n in binary : ${n.toBinaryString()}")
    println("  $n in hex    : ${n.toHexString()}")
    println("  $n isEven    : ${n.isEven()}")
    println("  $n isOdd     : ${n.isOdd()}")

    println("\n  clamp examples :")
    println("    50.clamp(0, 100)   = ${50.clamp(0, 100)}")
    println("    150.clamp(0, 100)  = ${150.clamp(0, 100)}")
    println("    -10.clamp(0, 100)  = ${(-10).clamp(0, 100)}")

    // ── 4. Summary ───────────────────────────────────────────────
    println("\n=== Summary ===")
    println("  @file:JvmName(\"Name\") at top of file :")
    println("    Kotlin file Utils.kt  → Java sees UtilsKt (default)")
    println("    With @file:JvmName    → Java sees Utils (clean)")
    println()
    println("  @JvmName on individual functions :")
    println("    Prevents signature clash when two functions have same")
    println("    bytecode signature (e.g., List<String> vs List<Int>)")
    println("    Java sees different names — no conflict")
}