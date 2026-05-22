package Exceptions

import java.io.File

fun main(array: Array<String>) {
    // reader is automatically closed at the end of the block, even if an exception is thrown
    // Kotlin — `use` extension on Closeable
    val firstLine = File("first.txt").bufferedReader().use { reader -> reader.readLine() }

    //what use do in intern
    val reader = File("readers.txt").bufferedReader()
    val firstLineInternally = try {
        reader.readLine()
    } finally {
        reader.close()
    }

}