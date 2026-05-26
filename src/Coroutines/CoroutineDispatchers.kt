package Coroutines

import kotlinx.coroutines.*

// ── Data classes ──────────────────────────────────────────────
data class User(val id: String, val name: String)
data class ProcessedData(val content: String, val wordCount: Int)

// ── Simulate operations ───────────────────────────────────────

// Simulates downloading raw text from a server (I/O — network)
suspend fun downloadFromNetwork(): String {
    delay(1000)
    return "hello world this is raw data from the server kotlin coroutines are awesome"
}

// Simulates heavy CPU work — counting and processing words
suspend fun heavyComputation(data: String): ProcessedData {
    delay(500)
    val words = data.trim().split(" ")
    val wordCount = words.size
    val content = words.joinToString(" ") { it.capitalize() }
    return ProcessedData(content = content, wordCount = wordCount)
}

// Simulates saving a user to a local database (I/O — disk)
suspend fun saveToDatabase(user: User) {
    withContext(Dispatchers.IO) {
        // Runs on IO pool — safe to do disk/db operations here
        delay(300)
        println("  [DB] User '${user.name}' saved on ${Thread.currentThread().name}")
    }
}

// Switches between dispatchers depending on the type of work:
// — Dispatchers.IO      → network/disk operations (pool of 64 threads)
// — Dispatchers.Default → CPU-heavy work (pool sized to number of CPU cores)
// Both share the same underlying thread pool — named DefaultDispatcher-worker-X
// Kotlin picks the first available thread — that's why the numbers vary
suspend fun loadAndProcess(): ProcessedData = withContext(Dispatchers.IO) {

    // Step 1 — download data on the IO pool (waiting for network response)
    // thread name: DefaultDispatcher-worker-X
    println("  [IO] Downloading on ${Thread.currentThread().name}")
    val rawData = downloadFromNetwork()
    println("  [IO] Download done — got ${rawData.length} characters")

    // Step 2 — switch to Default pool for CPU-heavy processing
    // withContext switches the active thread from IO pool to Default pool
    // thread name: DefaultDispatcher-worker-X (different worker, same pool)
    val result = withContext(Dispatchers.Default) {
        println("  [CPU] Processing on ${Thread.currentThread().name}")
        val processed = heavyComputation(rawData)
        println("  [CPU] Processing done — ${processed.wordCount} words")
        processed
    }

    // Step 3 — withContext finished, automatically back on IO pool
    // Kotlin resumes on whichever IO worker is free at this moment
    println("  [IO] Back on IO pool — ${Thread.currentThread().name}")
    result
}

// ── Entry point ───────────────────────────────────────────────
fun main() = runBlocking {
    // runBlocking runs on the "main" thread — blocks it until all coroutines finish
    // used here only because this is a top-level main function (not Android)

    println("── Step 1: Load and process data ──────────────")
    val processed = loadAndProcess()
    println("  Result  : ${processed.content}")
    println("  Words   : ${processed.wordCount}")

    println("\n── Step 2: Save user to database ──────────────")
    // saveToDatabase switches to IO internally — main thread is freed during the call
    val user = User(id = "42", name = "Abdelkader")
    saveToDatabase(user)

    println("\n── Done ────────────────────────────────────────")
}