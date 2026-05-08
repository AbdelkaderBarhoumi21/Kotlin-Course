package OOP

class Person(val name: String, val age: Int) {
    var email: String = ""

    // Secondary constructor — must call primary constructor first
    constructor(name: String, age: Int, email: String) : this(name, age) {
        this.email = email
    }
}

// In practice, default values usually replace secondary constructors
class User(val name: String, val age: Int, var email: String = "")

fun main(args: Array<String>) {

    val person = Person("John", 25) // primary constructor
    val person2 = Person("abdelkader", 25, "a.brh@gmail.com") // secondary constructor

    println("==========With secondary constructor==========")
    println(person.name)
    println(person.age)
    println(person.email) // "" : empty by default


    println(person2.name)
    println(person2.age)
    println(person2.email)

    // Same thing — with Default Values (simpler ✅)
    val user = User("Ahmed", 25)
    val user2 = User("Mourad", 27, "mourad@mail.com")
    println("==========With default values==========")
    println(user.name)
    println(user.age)
    println(user.email)


    println(user2.name)
    println(user2.age)
    println(user2.email)

}