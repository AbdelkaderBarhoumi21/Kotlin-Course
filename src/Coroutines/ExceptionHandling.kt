package Coroutines

import kotlinx.coroutines.*

// Simulates a flaky API
object FakeApi {
    suspend fun fetchData(shouldFail: Boolean): String {
        delay(100)
        if (shouldFail) throw RuntimeException("Network down!")
        return "Real data"
    }
}

// ════════════════════════════════════════════════════════════════
// 1. try-catch inside a suspend function
//    The simplest, most common pattern.
// ════════════════════════════════════════════════════════════════
suspend fun safeFetch(shouldFail: Boolean): String {
    return try {
        FakeApi.fetchData(shouldFail)
    } catch (e: Exception) {
        println("  [safeFetch] caught: ${e.message}")
        "Default"
    }
}

// ════════════════════════════════════════════════════════════════
// 2. CoroutineExceptionHandler
//    Catches UNCAUGHT exceptions from `launch`.
//    Only works on the ROOT of a coroutine hierarchy.
// ════════════════════════════════════════════════════════════════
val handler = CoroutineExceptionHandler { _, exception ->
    println("  [handler] caught: ${exception.message}")
}

// ════════════════════════════════════════════════════════════════
// 3. async + await
//    The exception is stored in the Deferred and rethrown at await()
// ════════════════════════════════════════════════════════════════
//   demonstrated inline in main()

@OptIn(DelicateCoroutinesApi::class)
fun main() = runBlocking {

    // ── DEMO 1: try-catch inside suspend ──────────────────────
    println("── 1. try-catch inside suspend ──")
    val result1 = safeFetch(shouldFail = true)
    println("  result: $result1")
    val result2 = safeFetch(shouldFail = false)
    println("  result: $result2\n")

    // ── DEMO 2: CoroutineExceptionHandler with launch ─────────
    // We use GlobalScope here to make the launch a ROOT coroutine.
    // Inside runBlocking, the handler wouldn't fire because the
    // exception would propagate to the parent (runBlocking) first.
    println("── 2. CoroutineExceptionHandler with launch ──")
    val job = GlobalScope.launch(handler) {
        delay(50)
        throw RuntimeException("Oops from launch!")
    }
    job.join()
    println("  (handler was called above)\n")

    // ── DEMO 3: async + await ─────────────────────────────────
    // The exception does NOT crash the coroutine immediately.
    // It's stored inside the Deferred and rethrown at await().
    println("── 3. async + await ──")
    val deferred = GlobalScope.async {
        delay(50)
        throw RuntimeException("Oops from async!")
    }
    try {
        deferred.await()
    } catch (e: Exception) {
        println("  [await] caught: ${e.message}")
    }
    println()

    // ── DEMO 4: async without await — exception is LOST! ──────
    // Common pitfall: if you never call await(), the exception
    // silently disappears (with GlobalScope/SupervisorJob).
    println("── 4. async without await — exception lost ──")
    val lostDeferred = GlobalScope.async {
        delay(50)
        throw RuntimeException("Nobody will ever see me!")
    }
    delay(200)   // give it time to fail
    println("  (no exception printed — it was swallowed)\n")

    // ── DEMO 5: supervisorScope — one child fails, others survive ──
    // With a normal coroutineScope, one failure cancels all siblings.
    // With supervisorScope, siblings keep running.
    println("── 5. supervisorScope — failure isolation ──")
    supervisorScope {
        launch(handler) {
            delay(50)
            throw RuntimeException("Child 1 failed!")
        }
        launch {
            delay(100)
            println("  Child 2 finished successfully")   // ← still runs!
        }
    }
    println()

    println("── Done ──")
}
