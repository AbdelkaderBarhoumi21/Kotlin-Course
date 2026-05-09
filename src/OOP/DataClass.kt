package OOP

/**
 * Data class automatically generates:
 *
 * - equals()      → compares by content, not memory address
 * - hashCode()    → integer code based on properties (used by HashMap/HashSet)
 * - toString()    → User(name=Alice, age=25)
 * - copy()        → creates a copy with optional modifications
 * - component1()  → name  ─┐
 * - component2()  → age   ─┘ enables destructuring: val (name, age) = user
 *
 * Example:
 *  val u1 = User("Alice", 25)
 *  val u2 = User("Alice", 25)
 *  u1 == u2           → true   (equals)
 *  u1.hashCode()      → same as u2.hashCode()
 *  println(u1)        → User(name=Alice, age=25)
 *  val u3 = u1.copy(age = 30)  → User(name=Alice, age=30)
 *  val (name, age) = u1        → destructuring
 */

data class UserData(val name: String, val age: Int) {
    var lastLogin: Long = 0L
}


fun main(args: Array<String>) {
    val user = UserData("Abdelkader", 26).apply {
        lastLogin = 100L
    }

    // Auto toString
    println(user)

    // Auto equals - content comparison
    val same = UserData("Abdelkader", 26).apply {
        lastLogin = 200L
    }
    println(same.equals(user)) // true — lastLogin is ignored


    // copy — creates a modified copy (original is unchanged)

    val older = user.copy(age = 23)
    println("new user object: $older")

    // Destructuring

    val (name, age) = user
    println("User name: $name")
    println("User age: $age")
}