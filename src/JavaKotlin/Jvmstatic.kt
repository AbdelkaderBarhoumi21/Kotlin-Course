package JavaKotlin

import kotlin.math.sqrt

// ── Class 1 : companion object WITHOUT @JvmStatic ────────────────
// From Kotlin → works fine
// From Java   → must call MathUtils.Companion.square(5) — ugly
class MathUtils {
    companion object {
        fun square(x: Int): Int = x * x
        fun cube(x: Int): Int = x * x * x
        val PI = 3.14159265358979
    }
}

// ── Class 2 : companion object WITH @JvmStatic ───────────────────
// From Kotlin → works fine (same as above)
// From Java   → can call MathUtils2.square(5) — clean
class MathUtils2 {
    companion object {
        @JvmStatic
        fun square(x: Int): Int = x * x

        @JvmStatic
        fun hypotenuse(a: Double, b: Double): Double = sqrt(a * a + b * b)

        @JvmStatic
        fun factorial(n: Int): Long {
            if (n <= 1) return 1
            return n * factorial(n - 1)
        }

        const val PI = 3.14159265358979   // const val = already static in Java
    }
}

// ── Class 3 : companion object as FACTORY ────────────────────────
// Very common pattern — companion object creates instances
class User private constructor(
    val name: String,
    val age: Int,
    val role: String
) {
    companion object {
        // Factory methods — create User in different ways
        @JvmStatic
        fun createAdmin(name: String): User {
            return User(name, 0, "ADMIN")
        }

        @JvmStatic
        fun createGuest(): User {
            return User("Guest", 0, "GUEST")
        }

        @JvmStatic
        fun create(name: String, age: Int): User {
            require(age >= 0) { "Age cannot be negative" }
            return User(name, age, "USER")
        }

        // Shared state — belongs to the CLASS, not instances
        var totalUsersCreated = 0
            private set

        fun trackCreation(): Unit {
            totalUsersCreated++
        }
    }

    override fun toString() = "User(name=$name, age=$age, role=$role)"
}

// ── Class 4 : companion object with CONSTANTS ────────────────────
class ApiConfig {
    companion object {
        const val BASE_URL = "https://api.example.com"   // compile-time constant
        const val API_VERSION = "v2"
        const val TIMEOUT_MS = 5000

        @JvmStatic
        fun fullUrl(endpoint: String): String {
            return "$BASE_URL/$API_VERSION/$endpoint"
        }
    }
}

fun main() {

    // ── 1. Basic companion object calls ──────────────────────────
    println("=== MathUtils companion object ===")
    println("  square(7)    : ${MathUtils.square(7)}")
    println("  cube(3)      : ${MathUtils.cube(3)}")
    println("  PI           : ${MathUtils.PI}")

    // ── 2. With @JvmStatic ───────────────────────────────────────
    println("\n=== MathUtils2 with @JvmStatic ===")
    println("  square(9)         : ${MathUtils2.square(9)}")
    println("  hypotenuse(3, 4)  : ${MathUtils2.hypotenuse(3.0, 4.0)}")
    println("  factorial(6)      : ${MathUtils2.factorial(6)}")
    println("  PI                : ${MathUtils2.PI}")

    // ── 3. Factory pattern ────────────────────────────────────────
    println("\n=== User factory (companion object) ===")

    val admin = User.createAdmin("Abdelkader")
    val guest = User.createGuest()
    val user = User.create("Ahmed", 25)

    println("  admin : $admin")
    println("  guest : $guest")
    println("  user  : $user")

    // ── 4. Shared state in companion ─────────────────────────────
    println("\n=== Shared state in companion object ===")

    User.trackCreation()
    User.trackCreation()
    User.trackCreation()

    println("  total users tracked : ${User.totalUsersCreated}")

    // All instances share the same companion
    println("  same value from any reference : ${User.totalUsersCreated}")

    // ── 5. Constants ─────────────────────────────────────────────
    println("\n=== ApiConfig constants ===")
    println("  BASE_URL   : ${ApiConfig.BASE_URL}")
    println("  VERSION    : ${ApiConfig.API_VERSION}")
    println("  TIMEOUT    : ${ApiConfig.TIMEOUT_MS}ms")
    println("  users url  : ${ApiConfig.fullUrl("users")}")
    println("  login url  : ${ApiConfig.fullUrl("auth/login")}")
}