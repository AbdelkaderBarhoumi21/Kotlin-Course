package File
// For modern path handling, the Java NIO API is fully usable from Kotlin:
import java.nio.file.*

fun main(args: Array<String>) {
    // Build a relative path: "data/users.txt"
    val path = Paths.get("data", "users.txt")

    // Convert the relative path to an absolute path based on the working directory
    val absolutePath = path.toAbsolutePath()
    println("Absolute path: $absolutePath")

    // Check if the file exists before trying to read it
    if (Files.exists(path)) {
        // Get the file size in bytes
        val size = Files.size(path)
        println("File size: $size bytes")

        // Read all lines into a list of strings
        val lines = Files.readAllLines(path)
        println("Lines: $lines")
    } else {
        println("File does not exist: $path")
    }

    // Create a directory tree all at once (no error if it already exists)
    Files.createDirectories(Paths.get("output", "logs"))
    println("Directory 'output/logs' created")

    // Walk the directory tree starting from the current directory
    // and print every file that ends with ".kt"
    Files.walk(Paths.get("."))
        .filter { it.toString().endsWith(".kt") }
        .forEach { println(it) }
}
