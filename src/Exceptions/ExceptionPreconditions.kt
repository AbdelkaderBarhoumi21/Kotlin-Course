package Exceptions

/*
 // Ce que Kotlin définit en interne :
 fun require(value: Boolean, lazyMessage: () -> Any) {
    if (!value) {
        val message = lazyMessage()  // ← appelé seulement si false
        throw IllegalArgumentException(message.toString())
    }
 }
 // Fonction avec lambda EN DERNIER paramètre :
fun require(value: Boolean, lazyMessage: () -> Any)
//                                       ↑ dernier → peut sortir des ()

 // Donc Kotlin t'autorise :
 require(true) { "message" }
 // au lieu de :
 require(true, { "message" })
 */
data class User(val name: String, val age: Int)

fun createUser(name: String, age: Int): User {
    // require — checks function arguments (throws IllegalArgumentException)

    require(name.isNotBlank()) { "Name must not be blank" }
    require(age > 0) { "Age must be greater than zero" }

    return User(name, age)

}

class Counter {
    private var initialized = false
    private var count = 0

    fun init() {
        initialized = true
    }

    fun reset() {
        check(initialized) { "Counter must be initialized first" }
        //  ↑ si false → IllegalStateException
        //  "c'est l'objet qui est dans un mauvais état"
        count = 0
    }
}


fun handle(value: Any): String = when (value) {
    is Int -> "Int: $value"
    is String -> "String: $value"
    else -> error("Unsupported type: ${value::class}")  // throws IllegalStateException
}

// requireNotNull / checkNotNull — assert non-null
fun process(name: String?) {
    val safeName = requireNotNull(name) { "Name must not be null" }
    // safeName is String (not String?) from here on
    println(safeName.length)
}

fun main(args: Array<String>) {
    // val user = createUser("   ", 25)
    val user = createUser("Alice", 25)
    println(user.toString())

    val c = Counter()
    c.reset()  // ❌ IllegalStateException: Counter must be initialized first
    c.init()
    c.reset()  // ✅ OK

    handle(1)      // ✅ "Int: 1"
    handle("hi")   // ✅ "String: hi"
    handle(3.14)   // ❌ IllegalStateException: Unsupported type

    process("hi")
    process("")


}