package Functions

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