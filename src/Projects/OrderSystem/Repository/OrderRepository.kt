package Projects.OrderSystem.Repository

import Projects.OrderSystem.Domain.OrderDto
import Projects.OrderSystem.Domain.OrderModel

interface OrderRepository {
    fun save(order: OrderModel)
    fun loadAll():List<OrderDto>
}