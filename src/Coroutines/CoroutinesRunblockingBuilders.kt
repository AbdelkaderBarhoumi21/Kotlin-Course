package Coroutines

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

// async — coroutine that returns a result
fun main(array: Array<String>) = runBlocking {
    val deferred: Deferred<Int> = async {
        delay(1000)
        42
    }
    println("Waiting...")
    val result = deferred.await()    // Wait for the result
    println("Result: $result")


}