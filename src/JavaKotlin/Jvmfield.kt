package JavaKotlin

// ── What happens WITHOUT @JvmField ───────────────────────────────
// Kotlin generates: private field + public getter
// Java must call: config.getVersion()
class ConfigWithoutJvmField {
    val version = "1.0.0"
    val debug = false
    val maxConn = 100
}

// ── What happens WITH @JvmField ───────────────────────────────────
// Kotlin generates: public field only (no getter)
// Java can access: config.version directly
class ConfigWithJvmField {
    @JvmField
    val version = "1.0.0"
    @JvmField
    val debug = false
    @JvmField
    val maxConn = 100
}

// ── Most common use : companion object constants ──────────────────
class HttpStatus {
    companion object {
        // const val — compile-time constant, already static in Java
        // no @JvmField needed for these
        const val OK = 200
        const val CREATED = 201
        const val BAD_REQUEST = 400
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
        const val INTERNAL_ERROR = 500

        // Non-const val in companion — needs @JvmField for Java direct access
        @JvmField
        val DEFAULT_MESSAGE = mapOf(
            200 to "OK",
            201 to "Created",
            400 to "Bad Request",
            401 to "Unauthorized",
            404 to "Not Found",
            500 to "Internal Server Error"
        )

        @JvmField
        val SUCCESS_RANGE = 200..299

        fun isSuccess(code: Int): Boolean = code in SUCCESS_RANGE
        fun message(code: Int): String = DEFAULT_MESSAGE[code] ?: "Unknown"
    }
}

// ── Practical example : Database config ──────────────────────────
class DatabaseConfig(
    @JvmField val host: String,
    @JvmField val port: Int,
    @JvmField val name: String,
    @JvmField val user: String
) {
    // Without @JvmField, Java would need:
    //   config.getHost(), config.getPort(), etc.
    // With @JvmField, Java can do:
    //   config.host, config.port, etc.

    fun connectionString(): String {
        return "jdbc:postgresql://$host:$port/$name?user=$user"
    }

    override fun toString(): String {
        return "DB($host:$port/$name)"
    }
}

fun main() {

    // ── 1. Without @JvmField — Kotlin side (same either way) ─────
    println("=== Without @JvmField (Kotlin side — no difference) ===")
    val c1 = ConfigWithoutJvmField()
    println("  version : ${c1.version}")
    println("  debug   : ${c1.debug}")
    println("  maxConn : ${c1.maxConn}")
    // Note: from Java, this would require c1.getVersion(), c1.getDebug(), etc.

    // ── 2. With @JvmField — Kotlin side (same) ───────────────────
    println("\n=== With @JvmField (Kotlin side — same syntax) ===")
    val c2 = ConfigWithJvmField()
    println("  version : ${c2.version}")
    println("  debug   : ${c2.debug}")
    println("  maxConn : ${c2.maxConn}")
    // Note: from Java, this allows config.version, config.debug, etc.

    // ── 3. HTTP status constants ──────────────────────────────────
    println("\n=== HttpStatus constants ===")
    println("  OK         : ${HttpStatus.OK}")
    println("  NOT_FOUND  : ${HttpStatus.NOT_FOUND}")
    println("  is 201 success? : ${HttpStatus.isSuccess(201)}")
    println("  is 404 success? : ${HttpStatus.isSuccess(404)}")
    println("  message 200 : ${HttpStatus.message(200)}")
    println("  message 500 : ${HttpStatus.message(500)}")

    // Direct access to @JvmField in companion
    println("  SUCCESS_RANGE : ${HttpStatus.SUCCESS_RANGE}")
    println("  250 in range? : ${250 in HttpStatus.SUCCESS_RANGE}")

    // ── 4. Database config ────────────────────────────────────────
    println("\n=== DatabaseConfig with @JvmField ===")
    val db = DatabaseConfig(
        host = "localhost",
        port = 5432,
        name = "mydb",
        user = "admin"
    )

    // From Kotlin — same with or without @JvmField
    println("  host   : ${db.host}")
    println("  port   : ${db.port}")
    println("  name   : ${db.name}")
    println("  url    : ${db.connectionString()}")

    // ── 5. Summary of the difference ─────────────────────────────
    println("\n=== Summary ===")
    println("  From Kotlin : NO difference — val x and @JvmField val x look the same")
    println("  From Java   : BIG difference")
    println("    Without @JvmField → config.getVersion()  (getter required)")
    println("    With    @JvmField → config.version       (direct field access)")
}