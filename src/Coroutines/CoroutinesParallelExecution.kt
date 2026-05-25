package Coroutines

import kotlinx.coroutines.*

// ── Data classes ──────────────────────────────────────────────
data class Profile(val name: String, val age: Int)
data class Order(val id: String, val item: String)
data class Product(val name: String, val price: Double)

// ── Simulate network calls (each takes different time) ────────

// Simulates loading a user profile from the server (1 sec)
suspend fun loadProfile(userId: String): Profile {
    delay(1000)
    return Profile(name = "Abdelkader", age = 25)
}

// Simulates loading the user's orders from the server (2 sec)
suspend fun loadOrders(userId: String): List<Order> {
    delay(2000)
    return listOf(
        Order(id = "001", item = "Keyboard"),
        Order(id = "002", item = "Mouse")
    )
}

// Simulates loading product recommendations (1.5 sec)
suspend fun loadRecommendations(userId: String): List<Product> {
    delay(1500)
    return listOf(
        Product(name = "Monitor", price = 299.99),
        Product(name = "Headset", price = 89.99)
    )
}

// ── Display the results ───────────────────────────────────────
fun showDashboard(
    profile: Profile,
    orders: List<Order>,
    recommendations: List<Product>
) {
    println("──────── Dashboard ────────")

    // Show profile info
    println("User    : ${profile.name}, age ${profile.age}")

    // Show each order
    println("Orders  :")
    orders.forEach { println("  • [${it.id}] ${it.item}") }

    // Show each recommendation
    println("For you :")
    recommendations.forEach { println("  • ${it.name} — $${it.price}") }

    println("───────────────────────────")
}

// ── Main ──────────────────────────────────────────────────────
fun main() = runBlocking {
    val userId = "123"

    val startTime = System.currentTimeMillis()

    // Launch all 3 calls IN PARALLEL — they all start at the same time
    val profile = async { loadProfile(userId) }        // starts immediately
    val orders = async { loadOrders(userId) }         // starts immediately
    val recommendations = async { loadRecommendations(userId) } // starts immediately

    // Wait for all 3 results before showing the dashboard
    // Total time ≈ 2 sec (slowest task) — NOT 1 + 2 + 1.5 = 4.5 sec
    showDashboard(
        profile.await(),          // wait for profile result
        orders.await(),           // wait for orders result
        recommendations.await()   // wait for recommendations result
    )

    val elapsed = System.currentTimeMillis() - startTime
    println("Loaded in ${elapsed}ms")  // ≈ 2000ms, not 4500ms
}