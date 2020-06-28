package com.learning.ddd.application.query

import com.learning.ddd.infra.command.OrderItemTable
import com.learning.ddd.infra.command.OrderTable
import org.jetbrains.exposed.sql.ResultRow
import java.math.BigDecimal
import java.util.UUID

data class DeliveryAddressProjection (val street: String, val number: Int, val city: String, val state: String, val complement: String) {
    companion object {
        fun rowToDeliveryAddressProjection(row: ResultRow) =
                row.let {
                    DeliveryAddressProjection(
                            street = it[OrderTable.street],
                            state = it[OrderTable.state],
                            complement = it[OrderTable.complement],
                            city = it[OrderTable.city],
                            number = it[OrderTable.number]
                    )
                }
    }
}

data class OrderItemProjection(val id: UUID, val product: UUID, val quantity: Int, val unitValue: BigDecimal) {
    companion object {
        fun rowToOrderItemProjection(row: ResultRow) = row.let {
            OrderItemProjection(
                    id = it[OrderItemTable.id],
                    product = it[OrderItemTable.product],
                    quantity = it[OrderItemTable.quantity].toInt(),
                    unitValue = it[OrderItemTable.unitValue]
            )
        }
    }
}

data class OrderProjection(val id: UUID, val customer: UUID, val deliveryAddress: DeliveryAddressProjection, val items: List<OrderItemProjection>) {
    companion object {
        fun rowToOrderProjection(row: ResultRow, items: List<OrderItemProjection>)=
                row.let {
                    OrderProjection(
                            id = it[OrderTable.id],
                            customer = it[OrderTable.customer],
                            deliveryAddress = DeliveryAddressProjection.rowToDeliveryAddressProjection(it),
                            items = items
                    )
                }
    }
}