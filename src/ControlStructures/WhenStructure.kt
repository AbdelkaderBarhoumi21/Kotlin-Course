package ControlStructures


fun main(args: Array<String>) {
    // 1. Basic when (Switch replacement)
    val day = "MONDAY"
    val type = when (day.uppercase()) {
        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> "Work day"
        "SATURDAY", "SUNDAY" -> "Weekend"
        else -> "Unknown day"
    }
    println("Day: $day | Category: $type")

    // 2. when with ranges
    val grade = 15
    val distinction = when (grade) {
        in 16..20 -> "Excellent"
        in 14..15 -> "Very Good"
        in 12..13 -> "Good"
        in 10..11 -> "Pass"
        in 0..9 -> "Fail"
        else -> "Invalid grade"
    }
    println("Grade: $grade | Distinction: $distinction")

    // 3. when with type checking (Smart Casts)
    // Local functions to test the logic
    fun describe(obj: Any): String = when (obj) {
        is String -> "Text of length ${obj.length}"
        is Int -> "Number doubled: ${obj * 2}"
        is Boolean -> if (obj) "True" else "False"
        is List<*> -> "List of ${obj.size} elements"
        else -> "Unknown type"
    }

    println("Type check (String): ${describe("Hello Kotlin")}")
    println("Type check (Int): ${describe(21)}")
    println("Type check (List): ${describe(listOf(1, 2, 3))}")

    // 4. when without argument (Conditional logic)
    fun classify(temperature: Int): String = when {
        temperature < 0 -> "Freezing"
        temperature < 10 -> "Cold"
        temperature < 20 -> "Chilly"
        temperature < 30 -> "Pleasant"
        temperature < 40 -> "Hot"
        else -> "Heatwave"
    }

    val currentTemp = 25
    println("Temperature: $currentTemp°C | Feeling: ${classify(currentTemp)}")
}