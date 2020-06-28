package com.learning.ddd.application.domain

import com.cross.domain.AggregateRoot
import com.cross.domain.Entity
import com.cross.domain.Notification
import com.cross.domain.ResultEntity
import com.cross.extensions.sumByCurrency
import com.cross.extensions.valueBiggerThanZero
import com.cross.vo.Currency
import java.time.LocalDateTime
import java.util.Optional
import java.util.UUID

enum class OrderStatus {
    OPEN,
    CLOSE
}

data class Order private constructor(val id: UUID = UUID.randomUUID(), val customer: UUID, val deliveryAddress: DeliveryAddress, val items: MutableList<OrderItem>) : Entity(), AggregateRoot {

    val status: OrderStatus = OrderStatus.OPEN

    val createdAt: LocalDateTime = LocalDateTime.now()

    val quantityOfItems: Int
        get() = items.size


    val amount: Currency
        get() = items.sumByCurrency { it.total }


    init {
        validate()
    }

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    override fun validators(): List<Optional<Notification>> = listOf(
            amount.valueBiggerThanZero("The total order must be greater than zero."),
            quantityOfItems.valueBiggerThanZero("List of items must have at least one item.")

    ) + deliveryAddress.validators() + items.flatMap { it.validators() }

    class Builder() {
        var id: UUID = UUID.randomUUID()
        lateinit var customer: UUID
        lateinit var deliveryAddress: DeliveryAddress
        lateinit var items: MutableList<OrderItem>

        fun build() : ResultEntity<List<Notification>, Order> {
            val newOrder = Order(id = id, customer = customer, deliveryAddress = deliveryAddress, items = items)

            return  when(newOrder.isValid()) {
                true -> ResultEntity.Success<Order>(newOrder)
                else -> ResultEntity.Failure(newOrder.notifications)
            }
        }

    }

}
