package operators

// Example 1 — + on a custom class
data class Vector(val x: Int, val y: Int) {

    // enables :  v1 + v2
    operator fun plus(other: Vector): Vector {
        return Vector(x + other.x, y + other.y)
    }

    // enables :  v1 - v2
    operator fun minus(other: Vector): Vector {
        return Vector(x - other.x, y - other.y)
    }

    // enables :  v1 * 3
    operator fun times(factor: Int): Vector {
        return Vector(x * factor, y * factor)
    }

    // enables :  -v1
    operator fun unaryMinus(): Vector {
        return Vector(-x, -y)
    }
}

// Example 2 — [] get and set
class Matrix(val rows: Int, val cols: Int) {
    // create an array of rows elements, where each element is a new IntArray of size cols
    private val data = Array(rows) { IntArray(cols) }

    // enables :  matrix[0, 1]
    operator fun get(row: Int, col: Int): Int {
        return data[row][col]
    }

    // enables :  matrix[0, 1] = 42
    operator fun set(row: Int, col: Int, value: Int) {
        data[row][col] = value
    }

}

// Example 3 — invoke() — call an object like a function
class Multiplier(private val factor: Int) {
    // enables Multiplier(5)
    operator fun invoke(value: Int): Int {
        return value * factor
    }
}

fun main(args: Array<String>) {
    // Example 1
    val v1 = Vector(10, 20)
    val v2 = Vector(20, 30)
    println(v1 + v2)    // Vector(x=4, y=6)
    println(v1 - v2)    // Vector(x=-2, y=-2)
    println(v1 * 3)     // Vector(x=3, y=6)
    println(-v1)        // Vector(x=-1, y=-2)
    // Example 2
    val matrix = Matrix(3, 3)
    matrix[0, 0] = 10    // calls set(0, 0, 10)
    matrix[1, 2] = 99    // calls set(1, 2, 99)
    println(matrix[0, 0]) // calls get(0, 0) → 10
    println(matrix[1, 2]) // calls get(1, 2) → 99
    // Example 3
    val double = Multiplier(2)
    val triple = Multiplier(3)
    println(double(5))
    println(triple(5))
    println(double(triple(4))) // double(12) = 24

}




