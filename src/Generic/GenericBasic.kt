package Generic

// Generic class
class Box<T>(var content: T) {
    fun show() = println("Content $content")
}
// Generic function

fun <T> first(list: List<T>): T = list.first()

fun main(args: Array<String>) {

    val textBox = Box("Hello") //T =String(inferred)
    textBox.show()
    val numberBox = Box(42) // T = Int (inferred)
    numberBox.show()

    val numberList = first(listOf(1, 2, 3, 4))
    println(numberList)
    val StringList = first(listOf<String>("Abdelkader", "Ahmed", "Ali"))
    println(StringList)

    fun <T : Comparable<T>> max(a: T, b: T): T = if (a > b) a else b
    println(max(1, 2))

    // Multiple constraints with `where`
    fun <T> sum(list: List<T>): Double where T : Number, T : Comparable<T> = list.sumOf {
        it.toDouble()
    }
    println(sum(listOf(1, 2, 3, 4)))


}