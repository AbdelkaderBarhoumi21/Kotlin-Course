package Projects.OrderSystem.Domain

data class CartItem(val product: Product, val quantity:Int){
    init {
        require(
            quantity in product.minQty..product.maxQty
        ){
            "Quantity $quantity out of range [${product.minQty},${product.maxQty}}] for ${product.name}"
        }
    }

    val subtotal:Double get() = product.price * quantity
    fun withQuantity(newQty:Int) = copy(quantity=newQty)
}
data class OrderItemDto(
    val product: String,
    val qty: Int,
    val subtotal: Double
) {
    // Manual JSON serialization
    fun toJson():String{
        return """ 
            {
            "product":"${product.escapeJson()},
            "qty:$qty,
            "subtotal":${"%.2f".format(subtotal)}
            }
            """
    }


    companion object {
        // Manual JSON deserialization
        fun fromJson(json: String): OrderItemDto {
            // Simple parser — assumes exact format from toJson()
            // For production, use a real parser or regex
            val product = json.substringAfter("\"product\":\"").substringBefore("\"")
            val qty = json.substringAfter("\"qty\":").substringBefore(",").toInt()
            val subtotal = json.substringAfter("\"subtotal\":").substringBefore("}").toDouble()
            return OrderItemDto(product, qty, subtotal)
        }

        // Helper to escape quotes in strings
        private fun String.escapeJson():String{
            return  this.replace("\"","\\\"")
        }
    }
}