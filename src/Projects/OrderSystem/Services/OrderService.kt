package Projects.OrderSystem.Services

import Projects.OrderSystem.Domain.OrderDto
import Projects.OrderSystem.Domain.OrderId
import Projects.OrderSystem.Domain.OrderModel
import Projects.OrderSystem.Repository.OrderRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OrderService(private val repository: OrderRepository){
    fun placeOrder(cart: CartServices): OrderModel{
        require(!cart.isEmpty()){
            "Cannot place an empty order"
        }
        val order= OrderModel(
            id = OrderId.generate(),
            items = cart.snapshot(),
            subtotal = cart.subtotal,
            discountAmount = cart.discountAmount,
            total = cart.total,
            placedAt = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )
        repository.save(order)
        cart.clear()
        return order
    }

    fun orderHistory():List<OrderDto> = repository.loadAll()
}