package Projects.OrderSystem.Discount

object NoDiscount : DiscountStrategy{
    override fun apply(subtotal:Double) =0.0
    override fun describe() = "No discount"
}