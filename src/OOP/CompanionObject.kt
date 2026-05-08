package OOP

class Circle(val radius: Double) {
    // companion object → equivalent of static members in Java
    companion object {
        const val PI = 3.14
        fun fromDiameter(diameter: Double): Circle = Circle(diameter / 2)
    }

    fun area() = PI * radius * radius


}

fun main(array: Array<String>) {
    val circle = Circle.fromDiameter(10.0)
    println(circle.area())
}