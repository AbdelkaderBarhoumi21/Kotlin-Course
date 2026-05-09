package OOP

//In Kotlin, classes are **final** by default. You must explicitly mark a class as `open` to allow inheritance.

// open → allows inheritance (by default, classes are final)
open class Animal(val name: String, val age: Int) {
    // open → allows overriding (by default, methods are final)
    open fun speak(): String = "$name makes a sound"

    fun introduce(): String = "$name ($age years old)"// Final by default

}

// name and age — no val/var because they belong to the parent
// breed — a val because it's a new field specific to Dog
class Dog(name: String, age: Int, val breed: String) : Animal(name, age) {
    override fun speak(): String = "$name barks: woof!" // Override

    fun fetch(item: String) = "$name fetch $item"

}

class Cat(name: String, age: Int) : Animal(name, age) {

    override fun speak() = "$name meows: Meow!"

    fun purr() = "$name is purring..."
}

fun main(args: Array<String>) {


    val rex = Dog("Rex", 3, "Labrador")
    println("======Dog=======")
    println(rex.speak())          // Rex barks: Woof!
    println(rex.introduce())      // Rex (3 years old)
    println(rex.fetch("ball"))    // Rex fetches ball

    val luna = Cat("Luna", 5)
    println("======Cat=======")
    println(luna.speak())         // Luna meows: Meow!
    println(luna.introduce())
    println(luna.purr())
}