package com.learning.ddd.application.command

import com.cross.events.EventPublisher
import com.learning.ddd.application.domain.Order
import com.learning.ddd.application.domain.OrderCommandRepository
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.any
import org.amshove.kluent.mock
import org.junit.jupiter.api.Test

import java.math.BigDecimal
import java.util.UUID

val anyOrder : Order = any()

internal class CreateOrderCommandHandlerTest {


    @Test
    fun `should proccessing a create order command with success`() {
        val createOrderCommand = CreateOrderCommand(
                customer = UUID.randomUUID(),
                deliveryAddress = DeliveryAddressDTO(
                        state = "RS",
                        complement = "NA",
                        city = "Porto Alegre",
                        number = 1983,
                        street = "Largo dos Campeoes"
                ),
                items = listOf(
                        OrderItemDTO(product = UUID.randomUUID(), quantity = 10, unitValue = BigDecimal.valueOf(341.00))
                )
        )

        val orderCommandRepository : OrderCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val createCommandHandler = CreateOrderCommandHandler(orderCommandRepository).also {
            it.eventPublisher = eventPublisher
        }


        createCommandHandler.handler(createOrderCommand)

        verify(orderCommandRepository, times(1)).insert(anyOrder)
        verify(eventPublisher, never()).publisher(any())
    }

    @Test
    fun `should throw domain event when command there is wrong information` () {
        val createOrderCommand = CreateOrderCommand(
                customer = UUID.randomUUID(),
                deliveryAddress = DeliveryAddressDTO(
                        state = "",
                        complement = "",
                        city = "",
                        number = 0,
                        street = ""
                ),
                items = listOf(
                        OrderItemDTO(product = UUID.randomUUID(), quantity = 0, unitValue = BigDecimal.ZERO)
                )
        )

        val orderCommandRepository : OrderCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val createCommandHandler = CreateOrderCommandHandler(orderCommandRepository).also {
            it.eventPublisher = eventPublisher
        }


        createCommandHandler.handler(createOrderCommand)

        verify(eventPublisher, times(1)).publisher(any())
        verify(orderCommandRepository, never()).insert(anyOrder)

    }
}