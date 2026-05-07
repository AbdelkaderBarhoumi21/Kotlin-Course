package ControlStructures

fun main(args: Array<String>) {

    // for with range
    for (i in 1..5) {
        print("$i ")

    }


    // for decrement
    for (i in 5 downTo 1) {
        print("$i ")
    }

    // for with step
    for (i in 1..20 step 2) {
        print("$i ")
    }


    // for-each

    val cars = listOf<String>("MAZDA", "GOLF", "FERRARI")
    for (car in cars) {
        print("$car ")
    }

    //for with index
    for ((index, car) in cars.withIndex()) {
        println("$index: $car")
    }

    //while and do while
    var counter = 0
    while (counter < 5) {
        println("index: ${counter++}")
    }

    // repeat
    repeat(5) { i ->
        println("Repeating $i")

    }


}