package OOP

interface Flyable {
    // Abstract method
    fun takeOff()
    fun land()

    // method with default implementation

    fun glide() = println("Gliding through the air.....")

    // Abstract property (no backing field) => No value stored in memory here — just a PROMISE
    val maxAltitude: Double
}

interface Swimmer {
    fun swim()
    fun dive()
    val maxDepth: Double
}

// Multiple interface implementation

class Duck(name: String, age: Int) : Animal(name, age), Flyable, Swimmer {
    override val maxAltitude: Double = 500.0
    override val maxDepth = 100.0

    override fun takeOff() = println("$name takes off!")
    override fun land() = println("$name lands.")
    override fun swim() = println("$name swims.")
    override fun dive() = println("$name dives.")

    override fun speak() = "$name quacks!"

    fun describe() = println("$name is $age years old")

}


fun main() {

    val duck = Duck("Donald", 3)

    // ── Animal methods ──────────────────────────────
    duck.describe()
    // Donald is 3 years old

    println(duck.speak())
    // Donald quacks!

    // ── Flyable ─────────────────────────────────────
    duck.takeOff()
    // Donald takes off!

    duck.glide()
    // Gliding through the air.....  ← default implementation

    duck.land()
    // Donald lands.

    println("Max altitude: ${duck.maxAltitude}m")
    // Max altitude: 500.0m

    // ── Swimmer ─────────────────────────────────────
    duck.swim()
    // Donald swims.

    duck.dive()
    // Donald dives.

    println("Max depth: ${duck.maxDepth}m")
    // Max depth: 100.0m

    // ── Polymorphism — Duck used as Flyable ──────────
    val flyer: Flyable = duck
    flyer.takeOff()    // Donald takes off!
    flyer.glide()      // Gliding through the air.....

    // ── Polymorphism — Duck used as Swimmer ──────────
    val swimmer: Swimmer = duck
    swimmer.swim()     // Donald swims.
    swimmer.dive()     // Donald dives.

    // ── Type checking ────────────────────────────────
    println(duck is Flyable)    // true
    println(duck is Swimmer)    // true
    println(duck is Animal)     // true
}