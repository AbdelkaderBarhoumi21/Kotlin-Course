package Functions

// function with block body
fun add(a: Int, b: Int): Int {
    return a + b
}

// function with expression body
fun add(a: Int, b: Int, c: Int): Int = a + b + c

//function without return type (unit=void en java )
fun display(mesage: String): Unit {
    println(mesage)
}

// functions with named params - Default values
// Default values — avoids method overloading

fun greet(name: String, greeting: String = "Good morning", punctuation: String = "!"): String =
    "$greeting, $name$punctuation"

fun main(args: Array<String>) {
    val result: Int = add(1, 2, 3)
    val result2 = add(1, 2)
    val message = display("Hello World!")
    val greetingResult = greet("Abdelkader")
    val greetingResult1 = greet("Abdelkader", "Good night")
    val greetingResult2 = greet("Abdelkader", "Good night", "?")
    val greetingResult3 = greet(punctuation = "?", name = "Abdelkader") // ordering doesnt matter

    println(greetingResult)
    println(greetingResult1)
    println(greetingResult2)
    println(greetingResult3)
    println(result)
    println(result2)

}