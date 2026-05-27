package Coroutines

import kotlinx.coroutines.*

// ── Simulate work ─────────────────────────────────────────────

suspend fun process(item: String) {
    delay(300)
    println("  ✓ processed: $item on ${Thread.currentThread().name}")
}

// ── 1. coroutineScope — all or nothing ────────────────────────
// Downloads a batch of files — waits for ALL to finish
// If one fails → all others are cancelled automatically

suspend fun processBatch(items: List<String>) = coroutineScope {
    // Launch one coroutine per item — all run in parallel
    items.forEach { item ->
        launch {
            process(item)
        }
    }

    // This line runs ONLY when every single coroutine above has finished
    println("  ✓ Batch complete — all ${items.size} items processed")
}

// Simulates one task crashing mid-batch
suspend fun cancellationExample() = coroutineScope {
    // Job1 — takes 1 sec
    val job1 = launch {
        delay(1000)
        println("  ✓ Job1 done")   // never reaches here — cancelled by job2's crash
    }
    // Job2 — crashes after 500ms
    // coroutineScope propagates this exception UP → job1 is cancelled too
    val job2 = launch {
        delay(500)
        throw RuntimeException("💥 Job2 crashed — cancelling everything!")
    }
}


// ── 2. supervisorScope — independent failures ─────────────────
// Each task is independent — one failure does NOT affect siblings

suspend fun independentExample() = supervisorScope {
    // Job1 — takes 1 sec — will finish normally
    val job1 = launch {
        delay(1000)
        println("  ✓ Job1 done — not affected by Job2's crash")
    }
    // Job2 — crashes after 500ms
    // supervisorScope isolates this failure — job1 keeps running
    val job2 = launch {
        delay(500)
        throw RuntimeException("💥 Job2 crashed — but Job1 continues!")
    }
    // Wait for both so we can see the full output
    job1.join() // join wait until the job finish
    job2.join()


}

// runBlocking does not create a new thread - the thread exists on the same page as it is called.
// When you launch sans withContext — the parent dispatcher's coroutine is created. The parent is running Blocking their tour on “main”
// with dispatcher — switch to IO pool => launch(Dispatchers.IO)


// coroutineScope → one crash = everyone cancels (teamwork)
// supervisorScope → one crash = only that person cancels (independent)
fun main(args: Array<String>) = runBlocking {
    // ── Example 1: processBatch — all succeed ─────────────────
    println("── Example 1: Batch processing (all succeed) ──")
    val files = listOf("file1.txt", "file2.txt", "file3.txt", "file4.txt")
    processBatch(files)


    // ── Example 2: cancellationExample — one fails ────────────
    println("\n── Example 2: coroutineScope — one crash cancels all ──")
    try {
        cancellationExample()

    } catch (e: RuntimeException) {
        // coroutineScope re-throws the exception to the parent
        println("  [CAUGHT] ${e.message}")
        println("  [CAUGHT] job1 was cancelled automatically")
    }
    // ── Example 3: independentExample — one fails, others live ─
    println("\n── Example 3: supervisorScope — failures are isolated ──")
    try {
        independentExample()
    } catch (e: RuntimeException) {
        // supervisorScope does NOT re-throw — job1 already finished
        println("  [CAUGHT] ${e.message}")
    }

    println("\n── Done ─────────────────────────────────────────")

}