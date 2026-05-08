package OOP

class Outer {
    private val outerField = 10

    // Nested class — does NOT have access to Outer's members (like static nested in Java)
    class Nested {
        fun show() = println("In Nested")
        // outerField is not accessible
    }

    // Inner class — HAS access to Outer's members
    inner class Inner {
        fun show() = println("Outer field is $outerField")
    }
}


fun main(array: Array<String>) {

    val nested = Outer.Nested()
    val inner = Outer().Inner() // Need an Outer instance
    inner.show()
    nested.show()

}