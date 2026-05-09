package OOP

// ── Simple Enum ───────────────────────────────────────
enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

// ── Enum with properties ──────────────────────────────

enum class Color(val hex: String, val rgb: String) {
    RED("#FF0000", "255, 0, 0"),
    GREEN("#00FF00", "0, 255, 0"),
    BLUE("#0000FF", "0, 0, 255"),
    BLACK("#000000", "0, 0, 0"),
    WHITE("#FFFFFF", "255, 255, 255");

    fun describe() = "$name -> hex=$hex | rgb: $rgb"
}

// ── Enum with properties + methods ───────────────────
enum class Planet(val mass: Double, val radius: Double) {
    MERCURY(3.303e+23, 2.4397e6),
    VENUS(4.869e+24, 6.0518e6),
    EARTH(5.976e+24, 6.37814e6),
    MARS(6.421e+23, 3.3972e6);

    // Computed property
    val gravity: Double
        get() = 6.67300E-11 * mass / (radius * radius)

    // Method
    fun weightOf(personMass: Double) = personMass * gravity

}

fun main(args: Array<String>) {
    // ── Simple Enum ───────────────────────────────────
    val direction = Direction.SOUTH;
    println("Direction: $direction")
    println("Direction name : ${direction.name}")
    println("Direction ordinal : ${direction.ordinal}")
    // ── when with enum — no else needed ───────────────
    val message = when (direction) {
        Direction.NORTH -> "Going up ⬆️"
        Direction.SOUTH -> "Going down ⬇️"
        Direction.EAST -> "Going right ➡️"
        Direction.WEST -> "Going left ⬅️"
    }

    println(message)

    // ── Enum with properties ──────────────────────────

    println(Color.RED.hex)
    println(Color.RED.rgb)
    println(Color.RED.describe())

    // ── iterate over all values ───────────────────────
    println("\n--- All Colors ---")

    Color.entries.forEach {
        println(it.describe())
    }
    /*
    Color.entries.forEach { color->
        println(color.describe())
    }

    */
    // ── Enum with methods ─────────────────────────────
    println("\n--- Planets ---")
    println("Earth gravity : ${"%.2f".format(Planet.EARTH.gravity)}")
    // Earth gravity : 9.80

    println("Mars gravity  : ${"%.2f".format(Planet.MARS.gravity)}")
    // Mars gravity  : 3.72

    println("Weight on Earth (75kg) : ${"%.1f".format(Planet.EARTH.weightOf(75.0))}")
    // Weight on Earth (75kg) : 735.2

    println("Weight on Mars  (75kg) : ${"%.1f".format(Planet.MARS.weightOf(75.0))}")
    // Weight on Mars  (75kg) : 278.8

    // ── iterate all planets ───────────────────────────
    println("\n--- All Planets ---")
    Planet.entries.forEach {
        println("${it.name.padEnd(10)} gravity = ${"%.2f".format(it.gravity)} m/s²")
    }
    // MERCURY    gravity = 3.70 m/s²
    // VENUS      gravity = 8.87 m/s²
    // EARTH      gravity = 9.80 m/s²
    // MARS       gravity = 3.72 m/s²

    // ── name + ordinal ────────────────────────────────
    println("\n--- Direction details ---")
    Direction.entries.forEach {
        println("${it.ordinal} → ${it.name}")
    }
    // 0 → NORTH
    // 1 → SOUTH
    // 2 → EAST
    // 3 → WEST

    // ── get enum from String ──────────────────────────
    val dir = Direction.valueOf(Direction.NORTH.name)
    println("Get Direction enums from String: $dir")
    val planet = Planet.valueOf("MARS")
    println("\n${planet.name} gravity: ${"%.2f".format(planet.gravity)}")
    // MARS gravity: 3.72


}