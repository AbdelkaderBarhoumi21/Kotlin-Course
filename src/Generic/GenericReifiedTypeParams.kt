package Generic

// In Java/regular Kotlin, generic types are erased at runtime (type erasure). Kotlin lets you **keep them** with `inline` + `reified`:
// Any en kotlin is equivalent of Object
inline fun <reified T> isType(value: Any): Boolean = value is T


// Filter a list by type — clean and type-safe
inline fun <reified T> List<*>.filterByType(): List<T> {
    return this.filterIsInstance<T>()
}

fun main(args: Array<String>) {

    isType<String>("Hello")  // true
    isType<Int>("World")    // false
    val mixed: List<Any> = listOf(1, "Hello", 2.0, "World", 42)
    val strings: List<String> = mixed.filterByType()    // ["Hello", "World"]
}