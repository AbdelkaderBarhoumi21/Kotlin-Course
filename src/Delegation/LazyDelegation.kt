package Delegation


fun loadFromDatabase(): List<String> {
    return listOf("Row 1", "Row 2", "Row 3")  // must return the actual data
}

class HeavyReport {
    val data: List<String> by lazy {
        println("Loading data...") // runs ONLY on first access
        loadFromDatabase()
    }
}

fun main() {
    val report = HeavyReport()
    println("Object created")   // data NOT loaded yet

    println(report.data)                     // → "Loading data..." then returns data
    println(report.data)                    // → returns cached data (no loading again)
    println(report.data)                   // → still cached
}