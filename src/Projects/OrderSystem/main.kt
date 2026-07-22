package Projects.OrderSystem

import Projects.OrderSystem.Discount.FixedDiscount
import Projects.OrderSystem.Discount.PercentageDiscount
import Projects.OrderSystem.Domain.Product
import Projects.OrderSystem.Repository.FileOrderRepository
import Projects.OrderSystem.Services.CartServices
import Projects.OrderSystem.Services.OrderService


fun main(args: Array<String>) {
    // --- Catalogue ---
    val apple = Product("P001", "Apple", 0.50, 1, 100)
    val laptop = Product("P002", "Laptop", 999.99, 1, 5)
    val book = Product("P003", "Clean Code", 34.90, 1, 20)

    // DIP
    val repo = FileOrderRepository("orders.json")
    val cartService = CartServices()
    val orderService = OrderService(repo)

    println("\n════════════════════════════════════")
    println("       ORDER SYSTEM — Kotlin        ")
    println("════════════════════════════════════")

    // Add products
    println("\n▶ Adding products to cart...")
    cartService.addProduct(apple, 6)
    cartService.addProduct(laptop, 1)
    cartService.addProduct(book, 2)
    cartService.printSummary()

    // --- Increment / Decrement ---
    println("\n▶ Incrementing Apple qty...")
    cartService.increment("P001")
    println("\n▶ Decrementing Laptop qty (min=1)...")
    cartService.decrement("P002")  // warns — already at min

    // --- Remove ---
    println("\n▶ Removing Book from cart...")
    cartService.removeItem("P003")

    // --- Discount ---
    println("\n▶ Applying 10% discount...")
    cartService.applyDiscount(PercentageDiscount(10.0))
    cartService.printSummary()
    // --- Place order ---
    println("\n▶ Placing order...")
    val order = orderService.placeOrder(cartService)
    println("  ✓ Order placed: ${order.id}")
    println("  ✓ Total paid:   ${order.total} €")
    println("  ✓ Saved to:     orders_kotlin.json")

    // --- Second order ---
    println("\n▶ New order with fixed discount...")
    cartService.addProduct(book, 3)
    cartService.applyDiscount(FixedDiscount(5.00))
    cartService.printSummary()
    val order2 = orderService.placeOrder(cartService)
    println("  ✓ Order placed: ${order2.id}")

    // --- Load history ---
    println("\n▶ Order history:")
    orderService.orderHistory().forEach { o ->
        println("  [${o.id}] ${o.placedAt} — total: ${o.total} €")
    }

    println("\n════════════════════════════════════")
    println("  All orders saved to orders_kotlin.json")
    println("════════════════════════════════════\n")

}






}