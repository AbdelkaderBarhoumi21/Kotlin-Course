package variables


fun main(args: Array<String>) {
    val name = "Abdelkader"
    val age = 25
    val message = "My name is $name and my age is $age"
    val info = " In 5 years my age is ${age + 5} years old"
    val html = """
    <html>
        <body>
            <h1>Bonjour $name</h1>
        </body>
    </html>
     """.trimIndent()

    println("message = $message")
    println("info = $info")
    println("html = $html")
}