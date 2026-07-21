package Projects.OrderSystem.Discount

data class FixedDiscount(val amount:Double): DiscountStrategy{
    override fun apply(subtotal:Double) = minOf(amount, subtotal    )
    override fun describe(): String = "%.2f fixed off".format(amount)
}