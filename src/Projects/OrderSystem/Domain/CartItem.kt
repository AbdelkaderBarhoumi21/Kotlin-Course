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