package variables

// A regex (Regular Expression) is a pattern — a way of describing text to be searched or manipulated.
fun main(array: Array<String>) {

    // in kotlin and java \ has a special role ( new line new space \n \t)
    // if you write \d kotlin understand \d as special character , he doesn't know => error
    // solution is to use \\
    // we write "\\d" and kotlin lit as  \d => kotlin consume the first \ and senc the second \d au regex
    // "\\d+" one or more digit
    // "\\w+" one or more digit/caracter

    // email verification
    val email = "test@gmail.com"
    val emailIsMatching = email.matches("\\w+@\\w+\\.\\w+".toRegex())  // → true
    println("Email is $email")
    println("Email matching:  $emailIsMatching ")

    // phone number verification (8 digit )
    val phone = "12345678"
    val phoneIsMatching = phone.matches("\\d{8}".toRegex())  // → true

    println("Phone number is $phone")
    println("Phone number matching:  $phoneIsMatching ")

    // find all digit into a string
    val text = "Alice has 22 years old and Bob has 20 years old."
    val allDigit = "\\d+".toRegex().findAll(text).map { it.value }.toList()
    println("All digit is $allDigit")

    // replace multiple space with one
    val result = "Hello  world".replace("\\s+".toRegex(), " ") // → "Hello World"
    println(result)

    // Create a regex
    val pattern = "\\d+".toRegex()

    // Find a match — searches for the first occurrence
    pattern.find("Alice is 22 years old")?.value   // → "22"

    // Check if the entire string matches the pattern
    "12345".matches("\\d+".toRegex())              // → true

    // Replace — replaces all matches with a new string
    "Hello   World".replace("\\s+".toRegex(), " ") // → "Hello World"

    // Split — splits the string wherever the pattern matches
    "Hello   World".split("\\s+".toRegex())

}
