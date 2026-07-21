package Projects.OrderSystem.Discount

interface DiscountStrategy {
    fun apply(subtotal: Double) : Double
    fun describe(): String
}