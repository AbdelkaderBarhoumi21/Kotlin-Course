package NullSafety

data class Address(val city: String?, val zipCode: String?)
data class User(val name: String, val address: Address?)

// Function that simulates fetching a user
fun getUser(hasUser: Boolean): User? {
    return when (hasUser) {
        true -> User("Ahmed", Address("Tunis", "1000"));
        false -> User("Abdelkader", null)
        // to simulate a null user, call getUser() with null directly
    }
}

fun getCity(user: User?): String = user?.address?.city ?: "Default address"

fun main() {
    // Case 1 — user with full address
    val user1 = getUser(true)
    println(getCity(user1))

    // Case 2 — user with no address
    val user2 = getUser(false)
    println(getCity(user2))

    val user3: User? = null
    println(getCity(user3))

    // Case 4 — user with city = null
    val user4 = User("Sami", Address(null, "2000"))
    println(getCity(user4))

    // Case 5 — print all details safely
    val users = listOf(user1, user2, user3, user4)

    users.forEachIndexed { index, user ->
        val name = user?.name ?: "No name"
        val city = user?.address?.city ?: "No city"
        val zipCode = user?.address?.zipCode ?: "No zip code"
        println("User ${index + 1} → name: $name | city: $city | zip: $zipCode")
    }

}