package JavaKotlin

// ─────────────────────────────────────────────────────────────────
// git commit: "learn(kotlin): calling Java classes and APIs directly from Kotlin"
// ─────────────────────────────────────────────────────────────────

import java.util.ArrayList
import java.util.LinkedList
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun main() {

    // ── 1. Java collections — work exactly like Kotlin ones ──────
    val list = ArrayList<String>()      // Java class
    list.add("Kotlin")
    list.add("Java")
    list.add("Interop")

    println("=== Java ArrayList ===")
    for (item in list) {
        println("  → $item")
    }

    // ── 2. Java LinkedList ───────────────────────────────────────
    val queue = LinkedList<Int>()
    queue.offer(1)   // Java method
    queue.offer(2)
    queue.offer(3)

    println("\n=== Java LinkedList (queue) ===")
    println("  peek (first without remove) : ${queue.peek()}")
    println("  poll (remove first)         : ${queue.poll()}")
    println("  remaining size              : ${queue.size}")

    // ── 3. Java Date/Time API ────────────────────────────────────
    val now = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
    val formatted = now.format(formatter)

    println("\n=== Java LocalDateTime ===")
    println("  now       : $now")
    println("  formatted : $formatted")

    // ── 4. Java String methods — all available ───────────────────
    val javaString = "  Hello from Java String  "

    println("\n=== Java String methods ===")
    println("  original  : '$javaString'")
    println("  trim()    : '${javaString.trim()}'")
    println("  toUpperCase() : '${javaString.trim().uppercase()}'")

    // ── 5. Java Math ─────────────────────────────────────────────
    println("\n=== Java Math ===")
    println("  Math.sqrt(16)  : ${Math.sqrt(16.0)}")
    println("  Math.pow(2, 8) : ${Math.pow(2.0, 8.0)}")
    println("  Math.abs(-42)  : ${Math.abs(-42)}")
}