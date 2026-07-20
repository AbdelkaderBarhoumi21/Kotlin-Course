package Projects.OrderSystem.Domain

@JvmInline
value class OrderId(val orderId:String){
    companion object{
        fun generate() = OrderId("ORD-${System.currentTimeMillis()}")
    }

    override fun toString()= orderId
}