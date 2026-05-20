package Generic

interface Source<out T> {
    fun get(): T
}

class ListSource<out T>(private val items: List<T>) : Source<T> {
    override fun get(): T = items.first()
}

interface Sink<in T> {
    fun send(item: T)
}

fun main() {

    // ─── out (covariant) ───
    // Source<String> peut être assigné à Source<Any>
    // parce que `out` garantit qu'on ne fait que LIRE T (jamais l'écrire)
    // → String est un sous-type de Any, donc Source<String> est un sous-type de Source<Any>
    val stringSource: Source<String> = ListSource(listOf("Hello", "World"))
    val anySource: Source<Any> = stringSource  // ✅ OK grâce à `out`

    println(stringSource.get()) // Hello  ← on lit un String
    println(anySource.get())    // Hello  ← le même objet, vu comme Any

    // ─── in (contravariant) ───
    // Sink<Any> peut être assigné à Sink<String>
    // parce que `in` garantit qu'on ne fait que CONSOMMER T (jamais le retourner)
    // → un Sink qui accepte Any peut forcément accepter un String
    val anySink: Sink<Any> = object : Sink<Any> {
        override fun send(item: Any) {
            println(item)
        }
    }
    val stringSink: Sink<String> = anySink  // ✅ OK grâce à `in`

    stringSink.send("Hello from stringSink") // Hello from stringSink ← String envoyé, Any reçu
    anySink.send(42)                         // 42                    ← Int envoyé, Any reçu
    anySink.send("Hello from anySink")       // Hello from anySink    ← String envoyé, Any reçu
}