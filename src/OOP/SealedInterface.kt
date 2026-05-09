package OOP

// ── Sealed Interfaces ────────────────────────────────
sealed interface ShapeData
sealed interface Drawable {
    fun draw()
}

sealed interface Resizable {
    fun resize(factor: Double): ShapeData
}

// ── Shapes ───────────────────────────────────────────
// Circle — Shape + Drawable + Resizable
data class CircleModel(val r: Double) : ShapeData, Drawable, Resizable {
    override fun draw() = println("Drawing Circle r=$r")
    override fun resize(factor: Double) = CircleModel(r * factor)
}

// Square — Shape + Drawable + Resizable
data class SquareModel(val side: Double) : ShapeData, Drawable, Resizable {
    override fun draw() = println("Drawing Square, side=$side")
    override fun resize(factor: Double) = SquareModel(side * factor)
}

// Invisible — Shape uniquement, pas Drawable


data object Invisible : ShapeData

// ── Handler functions ─────────────────────────────────
fun handleShape(shape: ShapeData) {
    when (shape) {
        is CircleModel -> println("Circle with r= ${shape.r}")
        is SquareModel -> println("Square  with side= ${shape.side}")
        is Invisible -> println("Invisible shape - nothing to show")
    }
}

fun handleDrawable(drawable: Drawable) {
    when (drawable) {
        is CircleModel -> drawable.draw()
        is SquareModel -> drawable.draw()
    }
}

fun handleResizable(resizable: Resizable, factor: Double) {
    when (resizable) {
        is CircleModel -> {
            val circleSize = resizable.resize(factor)
            println(circleSize)
        }

        is SquareModel -> {
            val squareSize = resizable.resize(factor)
            println(squareSize)
        }
    }
}

fun main(array: Array<String>) {
    val circle = CircleModel(5.0)
    val square = SquareModel(10.0)


    // ── handleShape ──────────────────────────────────

    handleShape(circle)
    handleShape(square)
    handleShape(Invisible)
    // ── handleDrawable ───────────────────────────────
    handleDrawable(circle)
    handleDrawable(square)

    // handleDrawable(Invisible) ← ❌ ERROR — Invisible is not Drawable
    // ── handleResize ───────────────────────────────────────
    handleResizable(circle, 2.0)
    handleResizable(square, 2.0)

    // ── Type checking ────────────────────────────────
    println(circle is ShapeData)     // true
    println(circle is Drawable)  // true
    println(circle is Resizable) // true

    println(Invisible is ShapeData)     // true
    println(Invisible is Drawable)  // false
    println(Invisible is Resizable) // false

    // ── data object toString ─────────────────────────
    println(Invisible)   // "Invisible" ✅


}