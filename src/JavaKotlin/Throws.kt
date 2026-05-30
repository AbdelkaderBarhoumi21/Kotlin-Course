package JavaKotlin

import java.io.File
import java.io.IOException
import java.lang.IllegalArgumentException

// ── Key concept ───────────────────────────────────────────────────
// Kotlin has NO checked exceptions — you never write "throws IOException"
// Java HAS checked exceptions — callers must catch or declare them
// @Throws bridges this gap for Java callers

// ── Without @Throws ───────────────────────────────────────────────
// Kotlin callers : fine, no catch required
// Java callers   : they don't know an IOException can be thrown
fun readFileUnsafe(path: String): String {
    return File(path).readText()  // can throw IOException — Java won't warn you
}

// ── With @Throws ──────────────────────────────────────────────────
// Kotlin callers : still no catch required (Kotlin ignores it)
// Java callers   : Java compiler forces them to catch IOException
@Throws(IOException::class)
fun readFileSafe(path: String): String {
    return File(path).readText()
}

// ── Multiple exceptions ───────────────────────────────────────────
@Throws(IOException::class, IllegalArgumentException::class)
fun readAndValidate(path: String, minLength: Int): String {
    require(minLength > 0) { "minLength must be positive" }   // throws IllegalArgumentException
    val content = File(path).readText()                        // throws IOException
    return content
}

// ── Practical example : file processor ───────────────────────────
class FileProcessor {

    @Throws(IOException::class)
    fun readLines(path: String): List<String> {
        return File(path).readLines()
    }

    @Throws(IOException::class)
    fun writeLines(path: String, lines: List<String>) {
        File(path).writeText(lines.joinToString("\n"))
    }

    @Throws(IOException::class, IllegalArgumentException::class)
    fun copyFile(source: String, destination: String) {
        require(source != destination) { "Source and destination must be different" }
        val content = File(source).readText()
        File(destination).writeText(content)
    }
}

// ── How Kotlin handles exceptions (no @Throws needed) ─────────────
fun demonstrateKotlinExceptionHandling() {
    println("\n=== Kotlin exception handling ===")

    // Kotlin uses try/catch like Java — but it's not enforced
    try {
        val content = readFileSafe("nonexistent.txt")
        println("  content : $content")
    } catch (e: IOException) {
        println("  caught IOException : ${e.message}")
    }

    // Kotlin can also use runCatching — functional style
    val result = runCatching {
        readFileSafe("nonexistent.txt")
    }

    result
        .onSuccess { println("  success : $it") }
        .onFailure { println("  failure : ${it.message}") }

    // Or use the result value
    val content = result.getOrElse { "default content" }
    println("  content or default : $content")
}

fun main() {

    // ── 1. Create a temp file to work with ────────────────────────
    val tempFile = createTempFile("test", ".txt")
    tempFile.writeText("Line 1\nLine 2\nLine 3\nLine 4\nLine 5")
    println("=== Temp file created : ${tempFile.path} ===")

    // ── 2. Read a file that exists ────────────────────────────────
    println("\n=== readFileSafe (file exists) ===")
    try {
        val content = readFileSafe(tempFile.path)
        println("  content : $content")
    } catch (e: IOException) {
        println("  error : ${e.message}")
    }

    // ── 3. Read a file that does NOT exist ───────────────────────
    println("\n=== readFileSafe (file missing) ===")
    try {
        val content = readFileSafe("missing_file.txt")
        println("  content : $content")
    } catch (e: IOException) {
        println("  caught : ${e.javaClass.simpleName} — ${e.message}")
    }

    // ── 4. Multiple exceptions ────────────────────────────────────
    println("\n=== readAndValidate ===")

    // valid call
    try {
        val content = readAndValidate(tempFile.path, 1)
        println("  valid : got ${content.length} chars")
    } catch (e: Exception) {
        println("  error : ${e.message}")
    }

    // invalid minLength
    try {
        readAndValidate(tempFile.path, -1)
    } catch (e: IllegalArgumentException) {
        println("  caught IllegalArgumentException : ${e.message}")
    }

    // ── 5. FileProcessor ─────────────────────────────────────────
    println("\n=== FileProcessor ===")
    val processor = FileProcessor()

    val lines = try {
        processor.readLines(tempFile.path)
    } catch (e: IOException) {
        emptyList()
    }
    println("  lines read : $lines")

    // ── 6. Kotlin exception handling styles ──────────────────────
    demonstrateKotlinExceptionHandling()

    // ── 7. Cleanup ────────────────────────────────────────────────
    tempFile.delete()
    println("\n=== Temp file deleted ===")

    // ── 8. Summary ───────────────────────────────────────────────
    println("\n=== Summary ===")
    println("  Kotlin  : no checked exceptions — @Throws is optional")
    println("  Java    : checked exceptions enforced by compiler")
    println("  @Throws : tells Java callers what exceptions to expect")
    println("  Without : Java compiles fine but won't warn about exceptions")
}