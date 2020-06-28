package com.learning.ddd.application.command

import com.cross.commands.BaseCommandHandler
import com.cross.domain.ResultEntity.Success
import com.cross.domain.ResultEntity.Failure
import com.cross.events.DomainInvalidEvent
import com.cross.infra.Log
import com.cross.vo.Currency
import com.cross.vo.Quantity
import com.learning.ddd.application.domain.DeliveryAddress
import com.learning.ddd.application.domain.Order
import com.learning.ddd.application.domain.OrderCommandRepository
import com.learning.ddd.application.domain.OrderItem
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateOrderCommandHandler (private val orderCommandRepository: OrderCommandRepository) : BaseCommandHandler<CreateOrderCommand>() {

    private val log = Log(CreateOrderCommand::class.java)

    override fun handler(command: CreateOrderCommand) {
        log.info("Received command $command")

        val orderResult = Order.build {
            id = command.id
            customer =command.customer
            deliveryAddress = command.deliveryAddress.let {
                  DeliveryAddress(street = it.street, number = it.number, state = it.state, city = it.city,complement = it.complement)
            }
            items = command.items.map {
                OrderItem(product = it.product, quantity = Quantity.of(it.quantity), unitValue = Currency.of(it.unitValue))
            }.toMutableList()
        }

        log.info("Transforming command $command to domain $orderResult")
        when(orderResult) {
            is Success -> orderCommandRepository.insert(orderResult.entity)
            is Failure -> eventPublisher.publisher(DomainInvalidEvent(orderResult.notifications))
        }
    }

}