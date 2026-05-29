package File

import java.io.File

fun main(args: Array<String>) {
    // Write to a file
    val file = File("src/File/test.txt")
    file.writeText("First line \nSecond line")
    // Append content
    file.appendText("\nThird line")

    // Read everything
    val content = file.readText()
    println("Content: $content")
    // Read line by line
    val lines: List<String> = file.readLines()
    println("Lines $lines")

    // Read with processing (efficient for large files — uses lazy Sequence)
    file.useLines { lines ->
        lines.filter { it.contains("line") }.map { it.uppercase() }.forEach { println(it) }
    }

    // Read raw bytes
    val bytes = File("src/File/test.png").readBytes()
    println("Bytes: $bytes")

    // Copy a file
    File("src/File/data.txt").copyTo(File("src/File/data2.txt"), overwrite = true)
    // List files in a directory
    File("src/File/").listFiles()?.forEach { file -> println("List of File: $file") }
    // Walk a directory recursively
    File("src/File/").walk().filter { file -> file.extension == "kt" }.forEach { file -> println("File Filter: $file") }

}