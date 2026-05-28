package Delegation

interface Printer {
    fun print(document: String)
    fun scan(): String
}

class BasicPrinter : Printer {
    override fun print(document: String) = println("Printing: $document")
    override fun scan(): String = "Document scan"
}

class ConnectedPrinter(private val printer: Printer) : Printer by printer {
    // Only print() is overridden — scan() is auto-delegated to `printer`
    override fun print(document: String) {
        println("[LOG] start")
        printer.print(document)
        println("[LOG] end")
    }
}

fun main() {
    // 1. Create a basic printer
    val basic = BasicPrinter()

    println("=== Testing BasicPrinter ===")
    basic.print("Report.pdf")
    println(basic.scan())

    // 2. Wrap it inside a ConnectedPrinter (decorator pattern via delegation)
    val connected = ConnectedPrinter(basic)

    println("\n=== Testing ConnectedPrinter ===")
    connected.print("Invoice.pdf")   // uses the overridden print() with logs
    println(connected.scan())        // auto-delegated to basic.scan() — no code written!
}
