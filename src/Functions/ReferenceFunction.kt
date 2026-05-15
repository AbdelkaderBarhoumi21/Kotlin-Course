package Functions


fun main(array: Array<String>) {
    fun isEven(X: Int) = X % 2 == 0
    val numbers = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    val evens = numbers.filter(::isEven) // Reference instead of { isEven(it) }

    // Reference to a member function
    data class Person(val name: String, val age: Int)

    val people = listOf(Person("Alice", 30), Person("Bob", 25))

    val names = people.map(Person::name)        // Reference to property
    println(names)
    val sortedByAge = people.sortedBy(Person::age)


}