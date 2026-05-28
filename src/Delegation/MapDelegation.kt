package Delegation

class ServerConfig(private val map: Map<String, Any?>) {
    val host: String by map   // reads map["host"]
    val port: Int by map      // reads map["port"]
    val ssl: Boolean by map   // reads map["ssl"]
}

fun main(args: Array<String>) {
    val config = ServerConfig(
        mapOf(
            "host" to "api.example.com",
            "port" to 443,
            "ssl" to true
        )
    )
    println(config.host)   // → api.example.com
    println(config.port)   // → 443
    println(config.ssl)    // → true
}