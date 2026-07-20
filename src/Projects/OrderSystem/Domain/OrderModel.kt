package Projects.OrderSystem.Domain
/*
 // What you write:
@JvmInline
value class OrderId(val value: String)

// What the compiler generates (conceptually):
final class OrderId {
    private final String value;   // ← the property

    public OrderId(String value) {  // ← constructor
        this.value = value;
    }

    public String getValue() {    // ← getter (Kotlin calls it .value)
        return this.value;
    }

    @Override
    public String toString() {
        return value;
    }

    // ... equals, hashCode, etc.
}
 */
data class OrderModel(
    val id: OrderId,
    val placedAt:String,
    val items:List<CartItem>,
    val subtotal:Double,
    val discountAmount:Double,
    val total:Double,
){
    fun toDto () = OrderDto(
        id = id.orderId,
        placedAt = placedAt,
        items = items.map {
            item -> OrderItemDto(
                item.product.name,
                item.quantity,
                item.subtotal,
            )
        },
        subtotal = subtotal,
        discountAmount= discountAmount,
        total = total,
    )
}