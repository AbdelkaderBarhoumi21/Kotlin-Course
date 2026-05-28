package Coroutines

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) = runBlocking {
    val channel = Channel<Int>()
    // Producer
    launch {
        for (i in 1..5) {
            channel.send(i)
            delay(100)
        }
        channel.close()
    }
    // Consumer
    for (value in channel) {
        println("Received $value")
    }

}