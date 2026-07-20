package Projects.OrderSystem.Domain

data class OrderDto(
    val id:String,
    val placedAt:String,
    val items:List<OrderItemDto>,
    val subtotal:Double,
    val discountAmount:Double,
    val total:Double,
){
    fun toJson():String{
        val itemsJson= items.joinToString(
            ",\n ",
            "[\n ",
            "\n ]",

        ){
            item -> item.toJson()
        }

        return """ 
            "id": "$id",
            "placedAt": "$placedAt",
            "items": $itemsJson,
            "subtotal": ${"%.2f".format(subtotal)},
            "discountAmount": ${"%.2f".format(discountAmount)}
            "total": ${"%.2f".format(total)}
        """.trimIndent()

    }
}