package com.learning.ddd.application.command

import com.cross.commands.BaseCommandHandler
import com.cross.domain.ResultEntity.Success
import com.cross.domain.ResultEntity.Failure
import com.cross.events.DomainInvalidEvent
import com.cross.events.EntityNotFoundEvent
import com.cross.infra.Log
import com.cross.vo.Currency
import com.learning.ddd.application.domain.Category
import com.learning.ddd.application.domain.CategoryCommandRepository
import com.learning.ddd.application.domain.Product
import com.learning.ddd.application.domain.ProductCommandRepository
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateProductCommandHandler(val productRepository: ProductCommandRepository, val categoryRepository: CategoryCommandRepository) : BaseCommandHandler<CreateProductCommand> () {

    private val log = Log(CreateProductCommandHandler::class.java)

    override fun handler(command: CreateProductCommand) {
        log.info("Received command ${command}")
        val categoryIsPresent : Optional<Category> = categoryRepository.findById(command.category)

        if (categoryIsPresent.isEmpty) {
            val message = "Category with id ${command.category} not found."
            log.info(message)
            eventPublisher.publisher(EntityNotFoundEvent(message))
            return
        }

        val productResult = Product.build {
            id = command.id
            name = command.name
            description = command.description
            value = Currency.of(amount = command.value)
            category = categoryIsPresent.get()
        }

        log.info("Transforming command ${command} to result entity ${productResult}")
        when(productResult) {
            is Success -> productRepository.insert(productResult.entity)
            is Failure -> eventPublisher.publisher(DomainInvalidEvent(productResult.notifications))
        }

    }

}