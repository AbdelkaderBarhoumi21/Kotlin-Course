package File

import java.io.File

fun main(args: Array<String>) {
    // .use {} — equivalent of try-with-resources in Java
    // Closes the resource automatically at the end of the block, even on exception
    // Buffered writing
    val file = File("src/File/Buffered.txt")
    file.bufferedWriter().use { writer ->
        repeat(10_000) { i ->
            writer.write("Line $i")
            writer.newLine()
        }
    }
// Buffered reading
    File("src/File/Buffered.txt").bufferedReader().use { reader ->
        var line = reader.readLine()
        while (line != null) {
            // process the line
            line = reader.readLine()
            println(line)
        }
    }
}


