package Collection

fun main(array: Array<String>) {
    // Eager (default) — each step creates a new list
    // Eager (default) — each step creates a new list
    val result = (1..1_000_000)
        .filter { it % 2 == 0 }      // Builds intermediate list of 500,000 elements
        .map { it * it }             // Builds another list of 500,000 elements
        .take(10)                    // Finally takes 10

    // Lazy (sequence) — operations fuse into a single pass
    val lazyResult = (1..1_000_000).asSequence()
        .filter { it % 2 == 0 }      // No intermediate list
        .map { it * it }             // No intermediate list
        .take(10)                    // Stops at 10
        .toList()                    // Materialize at the end


}