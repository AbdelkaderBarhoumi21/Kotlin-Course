package Projects.OrderSystem.Discount

data class PercentageDiscount( val percent:Double) : DiscountStrategy{
    init {
        require(
            percent in 0.0 .. 100.0
        ){"Percent must be 0-100"}
    }
    override fun apply(subtotal:Double) = subtotal  * percent /100.0
    override  fun describe() = "%.0f%% off".format(percent)

}