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

fun main(args: Array<String>) {
    val result: Int = add(1, 2, 3)
    val result2 = add(1, 2)
    val message = display("Hello World!")
    println(result)
    println(result2)

}