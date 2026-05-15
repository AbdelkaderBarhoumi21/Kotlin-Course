package Functions

fun main(array: Array<String>) {

    // single params
    val subs = { X: Int -> X - 2 }
    val subResult = subs(5)
    println("subResult: $subResult")
    // var type is a funtion => (Int, Int) -> Int
    val sum: (Int, Int) -> Int = { a, b -> a + b }
    val sumResult = sum(1, 2)
    println("Sum is $sumResult")
    // even without passing the type kotlin can know already the type
    val add = { x: Int, y: Int -> x + y }
    val addResult = add(1, 2)
    println("Addition is $addResult")
    // Trailing lambda — moved outside parentheses for readability
    val fruits = listOf("Apple", "Banana", "Orange")
    fruits.filter { it.length > 1 }  // ["Banana", "Orange"]
    fruits.filter({ it.length > 2 })


}