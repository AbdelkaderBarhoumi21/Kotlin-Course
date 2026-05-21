package Generic

// Function that works with any List, regardless of element type
fun printSize(list: List<*>) {
    println("Size:${list.size}")
}

fun main(array: Array<String>) {

    printSize(listOf(1, 2, 3, 4, 5, 6, 7, 8, 9))
    printSize(listOf("a", "b"))

}