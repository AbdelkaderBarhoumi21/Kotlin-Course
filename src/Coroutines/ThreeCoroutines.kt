package Coroutines

import kotlinx.coroutines.*

// ════════════════════════════════════════════════════════════════
// coroutineScope with 3 child coroutines
// The function returns ONLY when ALL 3 have finished
// ════════════════════════════════════════════════════════════════
suspend fun runThreeTasks() = coroutineScope {

    launch {
        println("  [Task 1] starting (will take 300ms)")
        delay(300)
        println("  [Task 1] DONE")
    }

    launch {
        println("  [Task 2] starting (will take 600ms)")
        delay(600)
        println("  [Task 2] DONE")
    }

    launch {
        println("  [Task 3] starting (will take 900ms)")
        delay(900)
        println("  [Task 3] DONE")
    }

    println("  >>> all 3 launched — coroutineScope now waits for them")
    // when this block ends, coroutineScope SUSPENDS until all 3 finish
}

fun main() = runBlocking {
    println("── Caller: before runThreeTasks() ──")
    val start = System.currentTimeMillis()

    runThreeTasks()   // ← suspends here until ALL 3 children finish

    val elapsed = System.currentTimeMillis() - start
    println("── Caller: after runThreeTasks() — took ${elapsed}ms ──")
    println("    (≈900ms — the slowest task; they ran IN PARALLEL,")
    println("     not 300+600+900=1800ms which would be sequential)")
}
