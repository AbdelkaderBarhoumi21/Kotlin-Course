package ControlStructures

fun main(args: Array<String>) {
    // Classic use
    val age = 20
    if (age >= 18) {
        println("Adult")
    } else {
        println("Minor")
    }

    // if as an expression — replaces the ternary operator
    //// ✅ Kotlin — pas d'opérateur ternaire, if est une expression
    // == String statut = (age >= 18) ? "Adulte" : "Mineur"; in JAVA
    val status = if (age >= 18) "Adult" else "Minor"

    // if-else if as an expression
    val grade = 15 // Added a value so the code compiles
    val distinction = if (grade >= 16) "Excellent"
    else if (grade >= 14) "Very Good"
    else if (grade >= 12) "Good"
    else if (grade >= 10) "Pass"
    else "Fail"


}