package Functions


fun main(args: Array<String>) {
    // ─── let ─── Run code if non-null, or transform a value
    val name: String? = "Ahmed"
    // Without let
    if (name != null) {
        println("Name is $name")
    }

    // With let
    name?.let {
        println("Uppercase name is ${it.uppercase()}")
    }

    // let to transform
    val len = name?.let {
        it.length
    } ?: 0
    println(len)

    // let to chain transformations
    val result = " Hello world ".let {
        it.trim()
    }.let { it.replaceFirstChar { c -> c.uppercase() } }.let {
        "$it!"
    }
    println(result)
    // ─── apply ─── Configure an object (returns the object)

    data class Server(var host: String = "", var port: Int = 0, var protocol: String = "http")


    val server = Server().apply {
        host = "localhost"
        port = 8080
        protocol = "https"
    }
    println(server.toString()) // `apply` returns the configured server

    // ─── also ─── Side effects (logging, debugging)

    val numbers = mutableListOf<Int>(1, 2, 3, 3, 4, 5).also {
        println("Initial list: $it")
    }.also {
        it.add(4)
    }.also { println("After add: $it") }

    // ─── run ─── Run a block and get a result (uses `this`)
    val runResult = server.run {
        "$protocol://$host:$port"
    }
    println(runResult)

    // run can also be called without a receiver — useful for grouping
    fun doSomething() {
        println("Doing something")
    }

    val now = run {
        val start = System.currentTimeMillis()
        doSomething()
        val end = System.currentTimeMillis()
        start - end
    }
    println(now)

    // ─── with ─── Group operations on an object (similar to run but different syntax)
    val info = with(server) {
        println("Connecting to: $host:$port")
        println("Port is $port")
        "$protocol://$host:$port"     // Returned value
    }
    println(info)

}