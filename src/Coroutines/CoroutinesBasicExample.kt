package Coroutines

import kotlinx.coroutines.*

fun main() = runBlocking {
    println("Start: ${Thread.currentThread().name}")

    // launch return job
    val job = launch {
        delay(1000L)
        println("Coroutine: ${Thread.currentThread().name}")
    }

    // async return Deferred
    val deferred = async {
        delay(500L)
        "Hello from async"
    }

    println(deferred.await()) // deferred.await() => wait for the result until finished with the value
    job.join() // join => wait until finished without value
    println("End")
}
