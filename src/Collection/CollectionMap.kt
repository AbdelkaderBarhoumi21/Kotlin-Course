package Collection

fun main(args: Array<String>) {
// Immutable map
    val capitals = mapOf(
        "France" to "Paris",
        "Tunisia" to "Tunis",
        "Japan" to "Tokyo"
    )
    // Access
    val paris = capitals["France"]                  // "Paris" (String?)
    val fallback = capitals.getOrDefault("USA", "Unknown")    // "Unknown"
    val lazy = capitals.getOrElse("USA") { "Computed default" }

    // Mutable map

    val scores = mutableMapOf<String, Int>()
    scores["Alice"] = 95
    scores["Bob"] = 82
    scores.putIfAbsent("Charlie", 90)
    scores.remove("Bob")


    // Iterate
    for ((country, capital) in capitals) {
        println("$country → $capital")
    }

    // Membership
    println("France" in capitals)               // true (checks keys)
    println("Tunis" in capitals.values)         // true
    println("Paris" in capitals.values)         // true
}