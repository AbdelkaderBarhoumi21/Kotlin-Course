package Projects.OrderSystem.Services

import Projects.OrderSystem.Discount.DiscountStrategy
import Projects.OrderSystem.Discount.*
import Projects.OrderSystem.Domain.CartItem
import Projects.OrderSystem.Domain.Product

class CartServices {
    private val items = linkedMapOf<String, CartItem>()
    private var discount: DiscountStrategy = NoDiscount

    fun addProduct(product: Product,qty:Int){
        items.merge(product.id, CartItem(product,qty)){
            exisiting,added -> exisiting.withQuantity(clamp(product,exisiting.quantity+ added.quantity))
        }
    }

    fun increment(productId:String)= updateQty(productId,+1)
    fun decrement(productId:String){
        val item=getItem(productId)
        if(item.quantity <=item.product.minQty){
            println("Minimum qty for ${item.product.name} is ${item.product.minQty} - remove it instead")
            return
        }
        updateQty(productId,-1)
    }
    fun removeItem(productId:String){
        checkNotNull((items.remove(productId))){
            "Product not in cart: ${productId}"
        }
        println("Removed from cart")
    }

    fun applyDiscount(
        discountStrategy: DiscountStrategy
    ){
        discount=discountStrategy
        println("Discount applied: ${discountStrategy.describe()}")
    }

    fun clear(){
        items.clear()
        discount = NoDiscount
    }

    fun isEmpty() = items.isEmpty()
    val subtotal:Double get() = items.values.sumOf { item -> item.subtotal }
    val discountAmount:Double get() = discount.apply(subtotal)
    val total:Double get() = subtotal- discountAmount

    fun snapshot():List<CartItem> = items.values.toList()

    fun printSummary() {
        if (items.isEmpty()) { println("  Cart is empty."); return }
        println("\n  ┌─────────────────────────────────────────────┐")
        println("  │                   CART                      │")
        println("  ├─────────────────────────────────────────────┤")
        items.values.forEach { i ->
            println("  │  %-20s x%-3d  %8.2f €  │".format(i.product.name, i.quantity, i.subtotal))
        }
        println("  ├─────────────────────────────────────────────┤")
        println("  │  Subtotal:                       %8.2f €  │".format(subtotal))
        println("  │  Discount (%-14s)    %8.2f €  │".format(discount.describe(), discountAmount))
        println("  ├─────────────────────────────────────────────┤")
        println("  │  TOTAL:                          %8.2f €  │".format(total))
        println("  └─────────────────────────────────────────────┘")
    }
    // ── private helpers ──
    private fun updateQty(productId:String,delta:Int){
        val item=getItem(productId)
        items[productId] = item.withQuantity(clamp(item.product,item.quantity +delta))
    }
    private fun getItem(productId:String) = items[productId] ?: throw NoSuchElementException("Product not in cart! $productId")
    private fun clamp(p:Product,qty:Int) = qty.coerceIn(p.minQty,p.maxQty)
}