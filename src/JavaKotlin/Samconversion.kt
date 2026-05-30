package JavaKotlin

// ─────────────────────────────────────────────────────────────────
// git commit: "learn(kotlin): SAM conversion — Kotlin lambdas as Java functional interfaces"
// ─────────────────────────────────────────────────────────────────

// SAM = Single Abstract Method
// A Java interface with exactly ONE abstract method
// Kotlin lambdas automatically convert to SAM interfaces

// ── Built-in Java SAM interfaces ─────────────────────────────────
// Runnable       → () -> Unit
// Comparator<T>  → (T, T) -> Int
// Callable<T>    → () -> T
// Predicate<T>   → (T) -> Boolean
// Function<T,R>  → (T) -> R
// Consumer<T>    → (T) -> Unit
// Supplier<T>    → () -> T

// ── Define your own SAM interface (Kotlin fun interface) ──────────
// "fun interface" = SAM interface defined in Kotlin
fun interface Validator<T> {
    fun validate(value: T): Boolean   // single abstract method
}

fun interface Transformer<T, R> {
    fun transform(input: T): R
}

fun interface EventHandler {
    fun handle(event: String, data: Any?)
}

// ── Class that uses SAM interfaces ───────────────────────────────
class Form(private val fields: Map<String, String>) {

    // Takes a SAM interface as parameter
    fun validate(fieldName: String, validator: Validator<String>): Boolean {
        val value = fields[fieldName] ?: return false
        return validator.validate(value)
    }

    fun process(fieldName: String, transformer: Transformer<String, String>): String {
        val value = fields[fieldName] ?: return ""
        return transformer.transform(value)
    }
}

// ── Class using Java's built-in functional interfaces ────────────
class DataPipeline<T>(private val data: List<T>) {

    // Uses Java's Predicate<T> — Kotlin lambda converts automatically
    fun filter(predicate: java.util.function.Predicate<T>): DataPipeline<T> {
        return DataPipeline(data.filter { predicate.test(it) })
    }

    // Uses Java's Function<T,R>
    fun <R> map(function: java.util.function.Function<T, R>): DataPipeline<R> {
        return DataPipeline(data.map { function.apply(it) })
    }

    // Uses Java's Consumer<T>
    fun forEach(consumer: java.util.function.Consumer<T>) {
        data.forEach { consumer.accept(it) }
    }

    fun toList(): List<T> = data
}

data class Product(val name: String, val price: Double, val category: String)

fun main() {

    // ── 1. Runnable — the simplest SAM ───────────────────────────
    println("=== Runnable (SAM) ===")

    // Without SAM — old Java style
    val runnableOld = object : Runnable {
        override fun run() {
            println("  running old style")
        }
    }
    runnableOld.run()

    // With SAM — Kotlin lambda converts automatically
    val runnableNew = Runnable { println("  running new style") }
    runnableNew.run()

    // Passed directly as lambda
    Thread { println("  running in thread") }.start()
    Thread.sleep(100) // wait for thread

    // ── 2. Comparator — sorting ───────────────────────────────────
    println("\n=== Comparator (SAM) ===")

    val words = mutableListOf("banana", "apple", "cherry", "date", "elderberry")

    // Without SAM
    words.sortWith(Comparator { a, b -> a.length - b.length })
    println("  sorted by length : $words")

    // With SAM shorthand (compareBy is even cleaner)
    words.sortWith(compareBy { it.length })
    println("  sorted by length : $words")

    words.sortWith(compareByDescending { it.length })
    println("  sorted desc      : $words")

    // ── 3. Custom Kotlin fun interface ────────────────────────────
    println("\n=== Custom fun interface (Validator) ===")

    // Lambda automatically converts to Validator<String>
    val notEmpty = Validator<String> { it.isNotBlank() }
    val minLength5 = Validator<String> { it.length >= 5 }
    val emailFormat = Validator<String> { it.contains("@") && it.contains(".") }
    val onlyLetters = Validator<String> { it.all { c -> c.isLetter() } }

    val testValues = listOf("", "hi", "Hello", "test@email.com", "hello123")

    for (value in testValues) {
        println("  '$value'")
        println("    notEmpty    : ${notEmpty.validate(value)}")
        println("    minLength5  : ${minLength5.validate(value)}")
        println("    emailFormat : ${emailFormat.validate(value)}")
        println("    onlyLetters : ${onlyLetters.validate(value)}")
    }

    // ── 4. Form with SAM validators ──────────────────────────────
    println("\n=== Form validation ===")

    val form = Form(
        mapOf(
            "username" to "ahmed123",
            "email" to "ahmed@example.com",
            "age" to "25"
        )
    )

    println("  username valid  : ${form.validate("username") { it.length >= 3 }}")
    println("  email valid     : ${form.validate("email") { it.contains("@") }}")
    println("  age valid       : ${form.validate("age") { it.toIntOrNull() != null }}")

    println("  username upper  : ${form.process("username") { it.uppercase() }}")
    println("  email domain    : ${form.process("email") { it.substringAfter("@") }}")

    // ── 5. DataPipeline with Java functional interfaces ───────────
    println("\n=== DataPipeline with Java SAM interfaces ===")

    val products = listOf(
        Product("Laptop", 1200.0, "Electronics"),
        Product("Phone", 800.0, "Electronics"),
        Product("Shirt", 30.0, "Clothing"),
        Product("Book", 15.0, "Education"),
        Product("Headphones", 150.0, "Electronics")
    )

    val pipeline = DataPipeline(products)

    // Lambda converts automatically to Java Predicate<Product>
    val expensive = pipeline
        .filter { it.price > 100.0 }               // Predicate<Product>
        .map { "${it.name} (${it.price}€)" }    // Function<Product, String>
        .toList()

    println("  expensive products (> 100€) :")
    expensive.forEach { println("    → $it") }

    // ── 6. EventHandler SAM ───────────────────────────────────────
    println("\n=== EventHandler SAM ===")

    val handlers = mutableMapOf<String, EventHandler>()

    // Register handlers as lambdas — auto SAM conversion
    handlers["login"] = EventHandler { event, data -> println("  [$event] user: $data") }
    handlers["logout"] = EventHandler { event, data -> println("  [$event] user: $data") }
    handlers["error"] = EventHandler { event, data -> println("  [$event] msg: $data") }

    // Fire events
    handlers["login"]?.handle("login", "ahmed")
    handlers["error"]?.handle("error", "Connection timeout")
    handlers["logout"]?.handle("logout", "ahmed")

    // ── 7. Summary ───────────────────────────────────────────────
    println("\n=== Summary ===")
    println("  SAM = interface with exactly ONE abstract method")
    println("  Kotlin lambda → Java SAM : automatic conversion")
    println("  Kotlin fun interface     : define your own SAM in Kotlin")
    println()
    println("  Most common Java SAMs :")
    println("    Runnable     →  { }                (no params, no return)")
    println("    Comparator   →  { a, b -> ... }    (two params, returns Int)")
    println("    Predicate    →  { it -> Boolean }  (one param, returns Boolean)")
    println("    Function     →  { it -> R }        (one param, returns R)")
    println("    Consumer     →  { it -> Unit }     (one param, no return)")
}