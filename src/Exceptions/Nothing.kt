package Exceptions

fun fail(message: String): Nothing {
    // Nothing = this function NEVER returns normally — it always crashes
    throw IllegalStateException(message)
}

fun findUser(id: Int): String? {
    // simulating a database — only user 1 exists
    return if (id == 1) "Abdelkader" else null
}

fun main() {

    // ── Case 1: value exists → fail() is never called ──
    val nameOrNull: String? = findUser(1)  // "Abdelkader"
    val name: String = nameOrNull ?: fail("Name cannot be null")
    // compiler knows: if nameOrNull is null → fail() crashes (Nothing)
    //                 if we reach this line  → name is guaranteed String
    println(name) // Abdelkader

    // ── Case 2: value is null → fail() is called → crash ──
    val missingUser: String? = findUser(99)  // null
    val name2: String = missingUser ?: fail("Name cannot be null")
    // fail() throws IllegalStateException — line below never runs
    println(name2)
}