package com.learning.ddd.infra.command

import com.cross.vo.Currency
import com.cross.vo.Quantity
import com.learning.ddd.application.domain.DeliveryAddress
import com.learning.ddd.application.domain.Order
import com.learning.ddd.application.domain.OrderItem
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object OrderTable : Table("sales.order") {
    val id = uuid("id")
    val customer = uuid("customer_id")
    val street = varchar(name = "street", length = 100)
    val number = integer(name = "number")
    val city = varchar(name = "city", length = 30)
    val state = varchar(name = "state", length = 2)
    val complement = varchar(name = "complement", length = 100)

    override val primaryKey = PrimaryKey(id)

    fun rowToOrder(row: ResultRow, items: MutableList<OrderItem>) = row.let {
         Order.build {
             this.id = it[OrderTable.id]
             this.deliveryAddress = DeliveryAddress(
                     street = it[OrderTable.street],
                     number = it[OrderTable.number],
                     city = it[OrderTable.city],
                     complement = it[OrderTable.complement],
                     state = it[OrderTable.state]
             )
             this.items = items
         }.entity()
    }
}

object OrderItemTable : Table("sales.order_item") {
    val id = uuid("id")
    val product = uuid("product_id")
    val quantity = double("quantity")
    val unitValue = decimal("unit_value", precision = 30, scale = 6)
    val orderId = uuid("order_id").references(OrderTable.id)
    override val primaryKey = PrimaryKey(id)

    fun rowToOrder(row: ResultRow) = row.let {
        OrderItem(
                product = it[product],
                quantity = Quantity.of(it[quantity]),
                unitValue = Currency.of(it[unitValue])
        )
    }
}