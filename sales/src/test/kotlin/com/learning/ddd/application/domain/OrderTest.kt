package com.learning.ddd.application.domain

import com.cross.domain.ResultEntity
import com.cross.vo.Currency
import com.cross.vo.Quantity
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.util.UUID

internal class OrderTest {

    @Test
    fun `should create a order with success`() {
        val deliveryAddressExpected = DeliveryAddress(street="Av Ipiranga", number = 1983, city = "Porto Alegre", state = "RS", complement = "AP 07")
        val customerExpected = UUID.randomUUID()


        val orderResult = Order.build {
            customer = customerExpected
            deliveryAddress = deliveryAddressExpected
            items = mutableListOf(
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.Companion.of(1), unitValue = Currency.of(amount = 5)),
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.Companion.of(2), unitValue = Currency.of(amount = 5)),
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.Companion.of(3), unitValue = Currency.of(amount = 5)))
        }

        when(orderResult) {
            is ResultEntity.Failure -> fail { "Order should be with success" }
            is ResultEntity.Success -> {
                val order = orderResult.entity
                deliveryAddressExpected `should be equal to` order.deliveryAddress
                3 `should be equal to` order.quantityOfItems
                Currency.of(amount = 30) `should be equal to` order.amount
                customerExpected `should be equal to` order.customer
                order.createdAt `should not be` null
                order.id `should not be` null
                OrderStatus.OPEN `should be equal to` order.status
            }
        }
    }

    @Test
    fun `should validate creation of order without items and with invalid address` () {
        val deliveryAddressExpected = DeliveryAddress(street="", number = 0, city = "", state = "", complement = "")
        val orderResult = Order.build {
            customer = UUID.randomUUID()
            deliveryAddress = deliveryAddressExpected
            items = mutableListOf()
        }

        when(orderResult) {
            is ResultEntity.Success -> fail { "Order should be with error" }
            is ResultEntity.Failure -> {
                val notifications = orderResult.notifications
                notifications.apply {
                    get(0).notification `should be equal to` "The total order must be greater than zero."
                    get(1).notification `should be equal to` "List of items must have at least one item."
                    get(2).notification `should be equal to` "Street is required."
                    get(3).notification `should be equal to` "City is required."
                    get(4).notification `should be equal to` "State is required."
                }
            }
        }
    }

    @Test
    fun `should validate creation of order with invalid items and invalid address` () {
        val deliveryAddressExpected = DeliveryAddress(street="", number = 0, city = "", state = "", complement = "")
        val orderResult = Order.build {
            customer = UUID.randomUUID()
            deliveryAddress = deliveryAddressExpected
            items = mutableListOf(
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.zero(), unitValue = Currency.zero())
            )
        }

        when(orderResult) {
            is ResultEntity.Success -> fail { "Order should be with error" }
            is ResultEntity.Failure -> {
                val notifications = orderResult.notifications
                notifications.apply {
                    get(0).notification `should be equal to` "The total order must be greater than zero."
                    get(1).notification `should be equal to` "Street is required."
                    get(2).notification `should be equal to` "City is required."
                    get(3).notification `should be equal to` "State is required."
                    get(4).notification `should be equal to` "Quantity should be bigger than zero."
                    get(5).notification `should be equal to` "Unit value should be bigger than zero."
                }
            }
        }
    }

    @Test
    fun `should validate creation of order with some invalid items and invalid address` () {
        val deliveryAddressExpected = DeliveryAddress(street="", number = 0, city = "", state = "", complement = "")
        val orderResult = Order.build {
            customer = UUID.randomUUID()
            deliveryAddress = deliveryAddressExpected
            items = mutableListOf(
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.zero(), unitValue = Currency.zero()),
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.of(1), unitValue = Currency.of(5.5)),
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.zero(), unitValue = Currency.zero()),
                    OrderItem(product = UUID.randomUUID(), quantity = Quantity.of(2), unitValue = Currency.of(3.75))
            )
        }

        when(orderResult) {
            is ResultEntity.Success -> fail { "Order should be with error" }
            is ResultEntity.Failure -> {
                val notifications = orderResult.notifications
                notifications.apply {
                    get(0).notification `should be equal to` "Street is required."
                    get(1).notification `should be equal to` "City is required."
                    get(2).notification `should be equal to` "State is required."
                    get(3).notification `should be equal to` "Quantity should be bigger than zero."
                    get(4).notification `should be equal to` "Unit value should be bigger than zero."
                    get(5).notification `should be equal to` "Quantity should be bigger than zero."
                    get(6).notification `should be equal to` "Unit value should be bigger than zero."
                }
            }
        }
    }
}