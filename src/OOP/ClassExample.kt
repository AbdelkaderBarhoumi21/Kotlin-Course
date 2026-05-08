package OOP

// Primary constructor in the class header

class Car(val brand: String, val color: String, var speed: Int = 0) {
    init {
        require(brand.isNotBlank()) { "Brand cannot be blank" }
        println("Car $brand created")
    }

    // Methods
    fun accelerate(
        increment: Int
    ) {
        speed += increment
        println("$brand is driving at $speed km/h")

    }

    fun brake() {
        speed = 0
        println("$brand has stopped")

    }

    // Computed property => calculated every time you try to access isMoving
    val isMoving get() = speed > 0

    override fun toString(): String = "Brand: $brand, Color: $color, Speed: $speed"
}


fun main(args: Array<String>) {
    val car = Car("MAZDA", "Black")
    car.accelerate(50)

    car.accelerate(30)

    println(car.isMoving)
    car.brake()
    println(car.isMoving)

    println(car)
}