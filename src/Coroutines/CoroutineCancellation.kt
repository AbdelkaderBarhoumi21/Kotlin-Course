package Coroutines

import kotlinx.coroutines.*

// Simulates a heavy computation
fun compute(i: Int): Double = Math.sqrt(i.toDouble())

// Simulates a cleanup operation (closing files, db connections...)
suspend fun cleanup() {
    println("  [CLEANUP] Releasing resources...")
    delay(200)
    println("  [CLEANUP] Done")
}

// ── 1. delay() — automatic cancellation check ─────────────────
// delay() is a suspension point — it checks for cancellation automatically
suspend fun autoCheckExample(scope: CoroutineScope): Job {
    return scope.launch {
        var i = 0
        while (true) {
            println("  [AUTO] Working iteration $i")
            i++
            delay(200)   // cancellation point — stops here if cancelled
        }
    }
}

// ── 2. isActive — manual cancellation check ───────────────────
// Used for tight CPU loops with no suspend functions inside
suspend fun isActiveExample(scope: CoroutineScope): Job {
    return scope.launch {
        var i = 0
        while (isActive) {    // checks cancellation on every iteration
            println("  [ACTIVE] Computing iteration $i")
            i++
            // no delay here — pure CPU work
            // without isActive check, cancel() would have no effect
        }
        // exits loop naturally when isActive becomes false
        println("  [ACTIVE] Loop exited cleanly")
    }
}

// ── 3. ensureActive() — throws if cancelled ───────────────────
// Used inside deep loops — throws CancellationException immediately
suspend fun ensureActiveExample() {
    for (i in 1..1_000_000) {
        currentCoroutineContext().ensureActive()   // throws CancellationException if cancelled
        compute(i)                                 // only runs if not cancelled
    }
}

// ── 4. finally + NonCancellable — cleanup after cancellation ──
// When a coroutine is cancelled, finally block still runs
// But regular suspend functions can't be called inside finally
// Use NonCancellable to allow suspend calls during cleanup
suspend fun withCleanupExample() {
    try {
        println("  [CLEANUP_EX] Starting heavy work...")
        ensureActiveExample()
        println("  [CLEANUP_EX] Heavy work done")   // skipped if cancelled
    } finally {
        // finally always runs — even after cancellation
        // but the coroutine is already cancelled here
        // so we need NonCancellable to call suspend functions
        withContext(NonCancellable) {
            cleanup()   // runs even though coroutine is cancelled
        }
    }
}

// ── Entry point ───────────────────────────────────────────────
fun main() = runBlocking {

    // ── Example 1: delay() auto check ─────────────────────────
    println("── Example 1: delay() — automatic check ──────────")
    val job1 = autoCheckExample(this)
    delay(500)                  // let it run for 500ms (~2 iterations)
    println("  Cancelling job1...")
    job1.cancelAndJoin()        // cancel + wait for it to finish
    println("  job1 cancelled\n")

    // ── Example 2: isActive manual check ──────────────────────
    println("── Example 2: isActive — manual check ───────────")
    val job2 = isActiveExample(this)
    delay(100)                  // let it run briefly
    println("  Cancelling job2...")
    job2.cancelAndJoin()        // isActive becomes false → loop exits cleanly
    println("  job2 cancelled\n")

    // ── Example 3: cleanup with NonCancellable ─────────────────
    println("── Example 3: finally + NonCancellable cleanup ───")
    val job3 = launch {
        withCleanupExample()
    }
    delay(100)
    println("  Cancelling job3...")
    job3.cancelAndJoin()        // triggers finally → cleanup runs
    println("  job3 cancelled\n")

    println("── Done ─────────────────────────────────────────")
}