package Exceptions

/*
 * ──────────────────────────────────────────────
 *  EXCEPTIONS — Java vs Kotlin
 * ──────────────────────────────────────────────
 *
 *  JAVA   → checked exceptions OBLIGATOIRES
 *           le compilateur force throws / try-catch
 *
 *  KOTLIN → pas de checked exceptions
 *           try-catch OPTIONNEL, seulement si utile
 *
 *  Syntaxe Kotlin :
 *
 *  1. Lancer une exception :
 *     throw IllegalArgumentException("message")
 *
 *  2. Catcher (optionnel) :
 *     try { ... }
 *     catch (e: IOException) { ... }
 *     catch (e: Exception)   { ... }  // catch-all
 *     finally { ... }                 // toujours exécuté
 *
 *  3. try comme expression (retourne une valeur) :
 *     val result = try { riskyCall() } catch (e: Exception) { "fallback" }
 *
 *  4. Annoter pour Java interop :
 *     @Throws(IOException::class)
 *     fun readFile(path: String): String { ... }
 * ──────────────────────────────────────────────
 */

// Kotlin ne te force à rien
fun readFile(path: String): String = java.io.File(path).readText()

// Tu catches seulement SI tu veux gérer l'erreur


fun main(args: Array<String>) {
    val content = try {
        readFile("file.txt")
    } catch (e: Exception) {
        "Default content"  // valeur de fallback
    }
    println(content)
}