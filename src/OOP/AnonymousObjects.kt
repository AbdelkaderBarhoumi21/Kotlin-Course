package OOP

interface Greeter {
    fun greet(name: String)
}


fun main(array: Array<String>) {
    // Anonymous object 1 — formal greeting
    val formalGreeter = object : Greeter {
        override fun greet(name: String) {
            println("Good morning, $name. How do you do?")
        }
    }

    // Anonymous object 2 — casual greeting
    val casualGreeter = object : Greeter {
        override fun greet(name: String) {
            println("Hey $name! What's up?")
        }
    }

    // Anonymous object 3 — no interface, just temp data
    val tempUser = object {
        val name = "Alice"
        val age = 25
        val email = "alice@gmail.com"
    }

    // Step 3 — Test it

    formalGreeter.greet("Ahmed")
    casualGreeter.greet("Mourad")
    println("${tempUser.name} is ${tempUser.age} years old")
    println("Email: ${tempUser.email}")


}