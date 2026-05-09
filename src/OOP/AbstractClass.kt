package OOP

// Abstract class — can hold state (properties with backing fields) = object stored in memory
// when you need shared state (instance fields), constructor logic, or a single inheritance hierarchy.
abstract class Shape(val color: String) {
    abstract fun area(): Double
    abstract fun perimeter(): Double

    // Concrete method that uses the abstract ones
    fun describe() = "Shape ($color) | Area = ${"%.2f".format(area())} | Perimeter = ${"%.2f".format(perimeter())}"
}

class CircleData(color: String, val radius: Double) : Shape(color) {
    override fun area() = Math.PI * radius * radius
    override fun perimeter() = 2 * Math.PI * radius
}

class Rectangle(color: String, val width: Double, val height: Double) : Shape(color) {
    override fun area() = width * height
    override fun perimeter() = 2 * (width + height)
}

fun main(args: Array<String>) {

    // ── Create shapes ────────────────────────────────
    val circle = CircleData("Red", 5.0)
    val rectangle = Rectangle("Blue", 4.0, 6.0)

    // ── area() ───────────────────────────────────────
    println(circle.area())        // 78.53981633974483
    println(rectangle.area())     // 24.0

    // ── perimeter() ──────────────────────────────────
    println(circle.perimeter())   // 31.41592653589793
    println(rectangle.perimeter()) // 20.0

    // ── describe() — concrete method from Shape ──────
    // uses area() and perimeter() internally
    // formats to 2 decimal places with "%.2f"

    println(circle.describe())
    println(rectangle.describe())

    // ── Polymorphism — list of Shape ─────────────────
    // both Circle and Rectangle are treated as Shape

    val shapes: List<Shape> = listOf(circle, rectangle)
    for (shape in shapes) {
        println(shape.describe())
    }

    // ── Cannot instantiate abstract class ────────────
    // val s = Shape("Green")      // ❌ ERROR — abstract class
}