package variables

fun main(args: Array<String>) {
    // var => Mutable
    // val => Immutable
    var username: String = "Abdelkader";
    username = "Barhoumi" // var is mutable

    val age: Int = 25
    // age=26 // 'val' cannot be reassigned.
    val email = "a.barhoumi@gmail.com" // The compiler automatically infers the type // Type infered : String


    // base types

    val integer: Int = 42                     // 4 bytes
    val longInteger: Long = 9_999_999_999L    // 8 bytes (underscore for readability)
    val decimal: Float = 3.14f               // 4 bytes
    val precise: Double = 3.14159265         // 8 bytes
    val byte: Byte = 127                     // 1 byte
    val short: Short = 32_000                // 2 bytes
    val character: Char = 'A'               // 2 bytes (Unicode)
    val boolean: Boolean = true              // 1 bit

    // No implicit conversion! (unlike Java)
    val i: Int = 42
    // val l: Long = i           // ❌ Error! No implicit conversion
    val l: Long = i.toLong()     // ✅ Explicit conversion required
    val d: Double = 42.toDouble()


    println("My name is $username")
    println("My email is $email")
    println("My age is $age")
    println("Double is $d")
    println("Type of d is ${d::class.qualifiedName}")
}