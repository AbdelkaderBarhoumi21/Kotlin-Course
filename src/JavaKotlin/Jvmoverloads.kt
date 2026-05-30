package JavaKotlin

// ── WITHOUT @JvmOverloads ─────────────────────────────────────────
// Kotlin default params work fine from Kotlin
// Java only sees ONE method — must always provide ALL params
class RequestWithoutOverloads {
    fun send(url: String, method: String = "GET", timeout: Int = 30): String {
        return "[$method] $url (timeout=${timeout}s)"
    }
}

// ── WITH @JvmOverloads ────────────────────────────────────────────
// Kotlin generates one method per default parameter
// Java sees 3 separate methods
class Request {
    @JvmOverloads
    fun send(
        url: String,
        method: String = "GET",
        timeout: Int = 30
    ): String {
        return "[$method] $url (timeout=${timeout}s)"
    }
}

// ── @JvmOverloads on CONSTRUCTOR ─────────────────────────────────
// Very common — constructors with many optional params
class Notification @JvmOverloads constructor(
    val title: String,
    val message: String = "",
    val priority: Int = 1,
    val silent: Boolean = false
) {
    override fun toString(): String {
        return "Notification(title='$title', msg='$message', priority=$priority, silent=$silent)"
    }
}

// ── Realistic example : HTTP client config ────────────────────────
class HttpClient @JvmOverloads constructor(
    val baseUrl: String,
    val timeout: Int = 5000,
    val retries: Int = 3,
    val debug: Boolean = false,
    val userAgent: String = "KotlinClient/1.0"
) {
    @JvmOverloads
    fun get(
        endpoint: String,
        headers: Map<String, String> = emptyMap(),
        params: Map<String, String> = emptyMap()
    ): String {
        val queryString = if (params.isEmpty()) ""
        else "?" + params.entries.joinToString("&") { "${it.key}=${it.value}" }

        val headerString = if (headers.isEmpty()) "none"
        else headers.entries.joinToString(", ") { "${it.key}: ${it.value}" }

        return buildString {
            appendLine("  GET $baseUrl$endpoint$queryString")
            appendLine("  headers : $headerString")
            appendLine("  timeout : ${timeout}ms")
            appendLine("  retries : $retries")
            appendLine("  debug   : $debug")
        }
    }

    @JvmOverloads
    fun post(
        endpoint: String,
        body: String = "",
        contentType: String = "application/json"
    ): String {
        return buildString {
            appendLine("  POST $baseUrl$endpoint")
            appendLine("  content-type : $contentType")
            appendLine("  body         : $body")
        }
    }

    override fun toString() =
        "HttpClient(url=$baseUrl, timeout=${timeout}ms, retries=$retries)"
}

fun main() {

    // ── 1. Basic @JvmOverloads demo ───────────────────────────────
    println("=== Request with @JvmOverloads ===")
    val request = Request()

    // Kotlin can call all of these — same as without @JvmOverloads
    println("  1 param  : ${request.send("https://api.com")}")
    println("  2 params : ${request.send("https://api.com", "POST")}")
    println("  3 params : ${request.send("https://api.com", "DELETE", 60)}")

    // ── 2. Without @JvmOverloads — Kotlin side only ───────────────
    println("\n=== Request without @JvmOverloads (Kotlin side same) ===")
    val r2 = RequestWithoutOverloads()
    println("  1 param  : ${r2.send("https://api.com")}")
    println("  2 params : ${r2.send("https://api.com", "POST")}")
    // Note: from Java, only r2.send(url, method, timeout) would work

    // ── 3. Constructor with @JvmOverloads ─────────────────────────
    println("\n=== Notification constructors ===")

    val n1 = Notification("Update available")
    val n2 = Notification("New message", "You have 3 unread messages")
    val n3 = Notification("Alert", "Disk space low", priority = 3)
    val n4 = Notification("Background sync", "Syncing...", 1, silent = true)

    println("  $n1")
    println("  $n2")
    println("  $n3")
    println("  $n4")

    // ── 4. HttpClient — realistic use ─────────────────────────────
    println("\n=== HttpClient ===")

    // Constructor with defaults — only baseUrl required
    val client = HttpClient("https://api.example.com")
    println("  client : $client")

    // Constructor with some overrides
    val debugClient = HttpClient(
        baseUrl = "https://api.example.com",
        timeout = 10000,
        debug = true
    )
    println("  debug  : $debugClient")

    // GET with no optional params
    println("\n--- GET minimal ---")
    print(client.get("/users"))

    // GET with params
    println("--- GET with params ---")
    print(client.get("/users", params = mapOf("page" to "1", "limit" to "10")))

    // GET with headers + params
    println("--- GET with headers + params ---")
    print(
        client.get(
            endpoint = "/users",
            headers = mapOf("Authorization" to "Bearer token123"),
            params = mapOf("role" to "admin")
        )
    )

    // POST minimal
    println("--- POST minimal ---")
    print(client.post("/users"))

    // POST with body
    println("--- POST with body ---")
    print(client.post("/users", """{"name":"Ahmed","age":25}"""))

    // ── 5. What Java would see ────────────────────────────────────
    println("=== What Java sees (simulated) ===")
    println("  @JvmOverloads generates these Java methods:")
    println("  send(String url)")
    println("  send(String url, String method)")
    println("  send(String url, String method, int timeout)")
    println()
    println("  Without @JvmOverloads, Java only sees:")
    println("  send(String url, String method, int timeout)  ← must always provide all")
}