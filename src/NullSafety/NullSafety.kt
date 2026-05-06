package NullSafety

fun main() {
    var username: String = "Abdelkader"
    // name = null// by default: type cannot be null
    // name.length // Compilation error : name can be null( impossible access to nullable )

    var email: String? = "b.Abdelkader@gmail.com" // type can be nullable using ?
    email = null
    println(username)
    println(email)

    // examples
    var name: String? = "Ahmed"

    // 1. Safe call operator (?.) — returns null if the object is null
    val len: Int? = name?.length        // 5 (or null if name is null)

    // 2. Elvis operator (?:) — default value if null
    val len1: Int = name?.length ?: 0    // 5, or 0 if name is null

    // 3. Non-null assertion (!!) — forces access (DANGEROUS — avoid it)
    val len2: Int = name!!.length        // 💥 If name is null → exception

    // 4. Safe cast (as?) — cast that returns null if impossible
    val obj: Any = "Hello"
    val str: String? = obj as? String       // "Hello"
    val num: Int? = obj as? Int             // null (no crash)

    // 5. let — execute code only if non-null
    name?.let {
        println("The name is: $it, length: ${it.length}")
    }
    // If name is null, the block does not execute at all


}