package Coroutines

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {

    // Our handler — only consulted on a ROOT coroutine
    val handler = CoroutineExceptionHandler { _, e ->
        println("✅ Handler caught: ${e.message}")
    }

    // ── CASE 1: Handler on a CHILD of runBlocking → IGNORED ──
    // Uncomment these 4 lines to see the program CRASH:
    //
    // launch(handler) {
    //     throw RuntimeException("Case 1: exception in a child")
    // }
    // delay(100)

    // ── CASE 2: GlobalScope.launch → Handler IS CALLED ──
    println("── Case 2: GlobalScope.launch (root) ──")
    val job = GlobalScope.launch(handler) {
        delay(50)
        throw RuntimeException("Case 2: exception in a root coroutine")
    }
    job.join()
    println("Program continues normally\n")

    // ── CASE 3: supervisorScope → Handler IS CALLED ──
    println("── Case 3: supervisorScope ──")
    supervisorScope {
        launch(handler) {
            delay(50)
            throw RuntimeException("Case 3: exception isolated by supervisor")
        }
        launch {
            delay(100)
            println("Sibling keeps running ✅")
        }
    }

    println("\n── End of program ──")
}
