package Projects

enum class Scale { CELSIUS, FAHRENHEIT, KELVIN }
// `when` as an expression — no break, exhaustive over the enum

fun toCelsius(value: Double, from: Scale): Double =
    when (from) {
        Scale.CELSIUS -> value
        Scale.FAHRENHEIT -> (value - 32) * 5 / 9
        Scale.KELVIN -> value - 273.15
    }


fun fromCelsius(celsius: Double, to: Scale): Double = when (to) {
    Scale.CELSIUS -> celsius
    Scale.FAHRENHEIT -> celsius * 9 / 5 + 32
    Scale.KELVIN -> celsius + 273.15
}

fun convert(value: Double, to: Scale, from: Scale): Double = fromCelsius(toCelsius(value, from), to)

fun parseScale(s: String): Scale = when (s.uppercase()) {
    "C" -> Scale.CELSIUS
    "F" -> Scale.FAHRENHEIT
    "K" -> Scale.KELVIN
    else -> throw IllegalArgumentException("Unknown scale: $s")
}

fun main(args: Array<String>) {
    print("Value: ")
    val value: Double = readln().toDouble() // readln() since Kotlin 1.6
    print("From (C/F/K): ")
    val from = parseScale(readln())
    print("To   (C/F/K): ")
    val to = parseScale(readln())

    val result = convert(value, from, to)
    println("%.2f $from = %.2f $to".format(value, result))

}