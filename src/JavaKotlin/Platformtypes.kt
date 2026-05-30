package JavaKotlin

// Simulating a Java-style class (with getters/setters)
// In a real project this would be a .java file
// Here we write it Kotlin-style to simulate the concept

class JavaStylePerson {
    // These properties in Kotlin generate get/set automatically
    // exactly like a Java class with getName()/setName()

    var name: String = ""
    var age: Int = 0
    var email: String? = null

    override fun toString(): String {
        return "Person(name='$name', age=$age, email=$email)"
    }
}


// A class that mimics what Java StringBuilder looks like from Kotlin
fun demonstrateJavaStringBuilder() {
    // StringBuilder is a Java class — works perfectly in Kotlin
    val sb = StringBuilder()
    // Java methods — called directly
    sb.append("hello")
    sb.append(", ")
    sb.append("World")
    sb.append("!")
    // Java property access — .length is actually .getLength() under the hood
    println("  length    : ${sb.length}")        // calls sb.getLength()
    println("  content   : ${sb.toString()}")     // calls sb.toString()

    // Java method — insert at index
    sb.insert(7, "Beautiful")
    println("  after insert : $sb")
    // Java method — delete range
    sb.delete(7, 17)
    println("  after delete : $sb")

}

fun main(args: Array<String>) {
    // ── 1. Property access on a class with getters/setters ───────
    val person = JavaStylePerson()
    // Kotlin syntax — calls setName() / setAge() / setEmail() internally
    person.name = "Ahmed"
    person.age = 25
    person.email = "ahmed@example.com"

    // Kotlin syntax — calls getName() / getAge() / getEmail() internally
    println("  name  : ${person.name}")    // → getName()
    println("  age   : ${person.age}")     // → getAge()
    println("  email : ${person.email}")   // → getEmail()
    println("  full  : $person")
    // ── 2. Java StringBuilder — property style ───────────────────
    println("\n=== Java StringBuilder ===")
    demonstrateJavaStringBuilder()


    // ── 3. Java System properties ────────────────────────────────
    println("\n=== Java System (property access) ===")

    val osName = System.getProperty("os.name")
    val javaVer = System.getProperty("java.version")
    val userHome = System.getProperty("user.home")
    println("  OS          : $osName")
    println("  Java ver    : $javaVer")
    println("  User home   : $userHome")

    // ── 4. Java Collections property access ──────────────────────
    val map = java.util.HashMap<String, Int>()
    map["score"] = 100     // Kotlin operator — calls map.put()
    map["level"] = 5

    // .size is actually .getSize() under the hood
    println("  size  : ${map.size}")       // → getSize()
    println("  score : ${map["score"]}")   // → get("score")
    println("  level : ${map["level"]}")


}