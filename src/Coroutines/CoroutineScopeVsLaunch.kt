package Coroutines

import kotlinx.coroutines.*

// ════════════════════════════════════════════════════════════════
// VERSION A — takes a scope, returns a Job (fire-and-forget)
// ════════════════════════════════════════════════════════════════
fun versionA(scope: CoroutineScope): Job {
    return scope.launch {
        repeat(3) { i ->
            println("  [A] working $i")
            delay(200)
        }
        println("  [A] finished")
    }
}

// ════════════════════════════════════════════════════════════════
// VERSION B — coroutineScope { } (blocks until inner work finishes)
// ════════════════════════════════════════════════════════════════
suspend fun versionB() = coroutineScope {
    launch {
        repeat(3) { i ->
            println("  [B] working $i")
            delay(200)
        }
        println("  [B] finished")
    }
}

// ════════════════════════════════════════════════════════════════
// PARALLEL DECOMPOSITION — the classic use of coroutineScope
// Launches profile + orders IN PARALLEL, returns only when BOTH done
// ════════════════════════════════════════════════════════════════
suspend fun loadDashboard(): String = coroutineScope {
    val profile = async {
        delay(500)
        "ProfileData"
    }
    val orders = async {
        delay(700)
        "OrdersData"
    }
    // suspends here until BOTH async finish
    "Dashboard(${profile.await()}, ${orders.await()})"
}

fun main() = runBlocking {

    // ── DEMO 1: Version A — caller continues immediately ──────
    println("── Version A: returns immediately ──")
    val start1 = System.currentTimeMillis()
    val job = versionA(this)
    println("  caller got Job back after ${System.currentTimeMillis() - start1}ms")
    println("  caller does other stuff while [A] runs in background...")
    delay(100)
    println("  caller still doing stuff...")
    job.join()   // explicitly wait for it
    println("  Version A total: ${System.currentTimeMillis() - start1}ms\n")

    // ── DEMO 2: Version B — caller is suspended until done ────
    println("── Version B: suspends caller until done ──")
    val start2 = System.currentTimeMillis()
    versionB()   // ← caller is BLOCKED here for ~600ms
    println("  caller resumes after ${System.currentTimeMillis() - start2}ms")
    println("  (notice: no 'caller doing stuff' messages possible)\n")

    // ── DEMO 3: Version A — cancellable from outside ──────────
    println("── Version A: cancellable via Job ──")
    val job2 = versionA(this)
    delay(250)
    println("  cancelling job2 from outside...")
    job2.cancelAndJoin()
    println("  job2 cancelled before finishing\n")

    // ── DEMO 4: coroutineScope for parallel decomposition ─────
    println("── coroutineScope for parallel work ──")
    val start4 = System.currentTimeMillis()
    val result = loadDashboard()
    val elapsed = System.currentTimeMillis() - start4
    println("  result: $result")
    println("  took ${elapsed}ms (≈700, not 1200 — they ran in parallel)\n")

    println("── Done ──")
}
