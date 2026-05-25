package Coroutines

import kotlinx.coroutines.*


suspend fun downalodData(): String {
    delay(1000)
    return "raw data"
}

suspend fun processData(data: String): String {
    delay(500)
    return data.uppercase()
}

fun main() = runBlocking {
    // Creates a coroutine scope (only for top-level / tests)

    // Sequential
    val data = downalodData()
    val result = processData(data)
    println(result)

    // Parallel — both tasks run concurrently
    val deferred1 = async { downalodData() }
    val deferred2 = async { downalodData() }
    println("Results: ${deferred1.await()}, ${deferred2.await()}")

}