package Collection

fun main(args: Array<String>) {
    // Immutable set
    val lang = setOf<String>("Kotlin", "Java", "Dart", "Rust", "Rust") // Duplicates removed
    println("Language: $lang")
    println("Language size : ${lang.size}")

    // Mutable set

    val mutableLang = mutableSetOf<String>("Java", "C++", "C")
    mutableLang.add("Kotlin")
    mutableLang.remove("C")
    println("Language : $mutableLang")
    println("Language size : ${mutableLang.size}")

    // Set operations
    val a = setOf(1, 2, 3, 4)
    val b = setOf(3, 4, 5, 6)

    val union = a union b
    println(union)

    val intersection = a intersect b // return only the element that it is  in a and b
    println(intersection)

    val difference = a subtract b // return only element that in a and absent in b (1,2)
    println(difference)


}