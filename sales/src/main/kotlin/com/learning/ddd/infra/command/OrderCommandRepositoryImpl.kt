package com.learning.ddd.infra.command

import com.cross.extensions.firstOrOptional
import com.learning.ddd.application.domain.Order
import com.learning.ddd.application.domain.OrderCommandRepository
import com.learning.ddd.application.domain.OrderItem
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Optional
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderCommandRepositoryImpl : OrderCommandRepository {

    override fun insert(order: Order) {
        transaction {
            OrderTable.insert {
                it[id] = order.id
                it[customer] = order.customer
                order.deliveryAddress.apply {
                    it[OrderTable.city] = this.city
                    it[OrderTable.state] = this.state
                    it[OrderTable.street] = this.street
                    it[OrderTable.complement] = this.complement
                    it[OrderTable.number] = this.number
                }
            }

            order.items.forEach{ orderItem ->
                OrderItemTable.insert {
                    it[id] = UUID.randomUUID()
                    it[product] = orderItem.product
                    it[quantity] = orderItem.quantity.value
                    it[unitValue] = orderItem.unitValue.amount
                    it[orderId] = order.id
                }
            }
        }
    }

    override fun findById(id: UUID): Optional<Order> {
        val items = mutableListOf<OrderItem>()
        return transaction {  OrderTable
                .innerJoin(otherTable = OrderItemTable)
                .select {
                    OrderTable.id.eq(id)
                }.toList().map {
                    items.add(OrderItemTable.rowToOrder(it))
                    it
                }.map {
                    OrderTable.rowToOrder(it, items)
                }.firstOrOptional()
        }
    }

}