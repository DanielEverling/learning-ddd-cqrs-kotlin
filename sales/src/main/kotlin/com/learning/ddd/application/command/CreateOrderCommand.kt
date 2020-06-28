package com.learning.ddd.application.command

import com.cross.commands.BaseCommand
import java.math.BigDecimal
import java.util.UUID

data class DeliveryAddressDTO (val street: String, val number: Int, val city: String, val state: String, val complement: String)
data class OrderItemDTO(val product: UUID, val quantity: Int, val unitValue: BigDecimal)

data class CreateOrderCommand(val customer: UUID, val deliveryAddress: DeliveryAddressDTO, val items: List<OrderItemDTO>) : BaseCommand()