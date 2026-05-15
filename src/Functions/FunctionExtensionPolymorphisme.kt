package Functions


// polymorphisme : once you call a function kotlin watch the runtime type and decide which verison to call
open class Shape {
    open fun describe() = "I'm a shape"
}

class Circle : Shape() {
    override fun describe() = "I'm a circle"
}

// function extension are not polymorphic
// mean static type win (the type declared)
open class Animal {}
class Cat : Animal() {}


fun main(args: Array<String>) {

    val shape: Shape = Circle()
    shape.describe() // "I'm a circle" => runtime type win
    // function extension
    fun Animal.describe() = println("Animal describe")
    fun Cat.describe() = println("Cat describe")
    val animal: Animal = Cat()
    animal.describe() // "Animal describe" => static type win (val animal: Animal you decalred  return type as Animal)
}