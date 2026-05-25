package Coroutines

import kotlinx.coroutines.*

// launch — fire-and-forget coroutine (no result)
fun main() = runBlocking {
    val job = launch {
        println("Task starting")
        delay(2000)
        println("Task done")
    }
    println("Task launched")
    job.join()     // Wait for completion

}