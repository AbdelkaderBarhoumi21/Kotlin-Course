package operators

fun main(args: Array<String>) {
    // ─── Ranges ───────────────────────────────────────────
    val range = 1..10       // 1 to 10 inclusive
    val rangeExclusive = 1..<10      // 1 to 9  (10 excluded)
    val rangeDescending = 10 downTo 1 // 10 to 1
    val rangeWithStep = 1..20 step 3// 1, 4, 7, 10, 13, 16, 19

    println("=== Ranges ===")
    println("1..10        → $range")
    println("1..<10       → $rangeExclusive")
    println("10 downTo 1  → ${(10 downTo 1).toList()}")
    println("1..20 step 3 → ${(1..20 step 3).toList()}")


    // ─── Iterating over ranges ─────────────────────────────
    println("\n=== Iterating ===")

    print("1..5          → ")
    for (i in 1..5) print("$i ")          // 1 2 3 4 5
    println()

    print("1..<5         → ")
    for (i in 1..<5) print("$i ")         // 1 2 3 4
    println()

    print("5 downTo 1    → ")
    for (i in 5 downTo 1) print("$i ")    // 5 4 3 2 1
    println()

    print("1..10 step 2  → ")
    for (i in 1..10 step 2) print("$i ")  // 1 3 5 7 9
    println()

    // ─── Membership check ─────────────────────────────────
    val x = 5

    println("\n=== Membership Check ===")
    println("$x in  1..10  → ${x in 1..10}")    // true
    println("$x in  6..10  → ${x in 6..10}")    // false
    println("$x !in 20..30 → ${x !in 20..30}")  // true
    println("$x !in 1..10  → ${x !in 1..10}")   // false


    // ─── Destructuring ────────────────────────────────────
    println("\n=== Destructuring ===")

    // Pair
    val pair = Pair("barhoumi", 25);
    val pairName = pair.first
    val pairAge = pair.second;
    println(pairName)
    println(pairAge)

    // simple data class that holds 2 values(already in kotlin stdlib)
    val (name, age) = Pair("Abdelkader", 25)


    println("My name is $name and I am $age years old")

    // Triple
    val (city, country, population) = Triple("Tunis", "Tunisia", 1_100_000)
    println("City: $city | Country: $country | Population: $population")

    val people = listOf(
        Pair("John", 25),
        Pair("Doe", 26),
        Pair("Neymar", 27),
    );
    println("\n=== Destructuring in a loop ===")

    for ((personName, personAge) in people) {
        println("$personName is $personAge years old")
    }


}