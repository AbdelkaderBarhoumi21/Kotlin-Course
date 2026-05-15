package Functions
// Extensions are **resolved statically** — they're really top-level functions with the receiver as the first parameter. The compiler translates:
/*
fun String.wordCount(): Int = this.trim().split("\\s+".toRegex()).size
"hello".wordCount()
*/
// …into something equivalent to:
/*
fun wordCount(receiver: String): Int = receiver.trim().split("\\s+".toRegex()).size
wordCount("hello")
 */

/*
This means extensions don't actually modify the class. Two consequences:

1. **No runtime overhead** beyond a regular function call.
2. **They're not polymorphic** — extension calls are dispatched based on the *static* type, not the runtime type.
 */

// **Problem solved:** In Java, to add functionality to a class you don't control (like `String`),
// you create utility classes (`StringUtils`, `CollectionUtils`...). Kotlin extensions read naturally and IDE autocomplete works seamlessly.
/*
// ❌ Java — cannot add methods to String
// Solution: a static utility class
public class StringUtils {
    public static int wordCount(String s) {
        return s.trim().split("\\s+").length;
    }
}
StringUtils.wordCount("Hello there");  // Awkward
 */

// Extension property => must be in top level (dont add inside fucntion, or in bloc {})
val String.firstLetter: Char
    get() = this.first()

fun main(array: Array<String>) {
    // Add a method to String
    // split a string into a list due to a sperator("\\s+) in our case \\s = mean white space
    fun String.wordCount(): Int = this.trim()
        .split("\\s+".toRegex()).size

    val stringCount = "My name is Abdelkader".wordCount()
    println("$stringCount")

    // Add method to int
    fun Int.isEven(): Boolean = this % 2 == 0
    fun Int.isOdd(): Boolean = this % 2 != 0

    println("${42.isOdd()}")
    println("${42.isEven()}")

    // Extension on a nullable type

    fun String?.orEmpty2(): String =
        this ?: "" // ?: "" if null return "" else return this (string) == name ?? "" en dart

    val name: String? = null
    println(name.orEmpty2())   // "" (no NullPointerException!)

    "Kotlin".firstLetter    // 'K'


    // Generic extension
    fun <T> List<T>.second(): T {
        if (size < 2) throw NoSuchElementException("List has fewer than 2 elements")
        return this[1]
        listOf(10, 20, 30).second()    // 20
    }
}