package Collection

fun main(args: Array<String>) {
    // IMMUTABLE list (default — recommended)
    val fruits = listOf<String>("apple", "banana", "orange")
    // fruits.add("Kiwi")       // ❌ Error — no add() on a read-only list

    // Mutable list
    val mutableFruits = mutableListOf<String>("apple", "banana")
    mutableFruits.add("orange")
    mutableFruits.add("Kiwi")
    mutableFruits.removeAt(0)
    mutableFruits[0] = "Tomato"
    println("Mutable Fruits: $mutableFruits")

    // Empty typed list
    val numbers = mutableListOf<Int>()
    numbers.addAll(listOf(1, 2, 3))
    println("Empty type list: $numbers")

    // Common operations
    val size = fruits.size
    println("Number of fruits: $size")

    val first = fruits.first()
    println("First fruit: $first")

    val last = fruits.last()
    println("Last fruit: $last")

    val contains = fruits.contains("apple")
    println("Contains: $contains")

    val index = fruits.indexOf("orange")
    println("Index: $index")
    // Returns the first element matching the given predicate, or null if element was not found
    val firstOrNull = fruits.firstOrNull { it == "test" }
    println("First fruit: $firstOrNull")


}