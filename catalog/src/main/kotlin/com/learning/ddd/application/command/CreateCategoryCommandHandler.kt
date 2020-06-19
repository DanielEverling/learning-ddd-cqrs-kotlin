package com.learning.ddd.application.command

import com.cross.commands.BaseCommandHandler
import com.cross.domain.Notification
import com.cross.domain.ResultEntity
import com.cross.domain.ResultEntity.Success
import com.cross.domain.ResultEntity.Failure
import com.cross.events.DomainInvalidEvent
import com.cross.infra.Log
import com.learning.ddd.application.domain.Category
import com.learning.ddd.application.domain.CategoryCommandRepository
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CreateCategoryCommandHandler(val categoryRepository: CategoryCommandRepository) : BaseCommandHandler<CreateCategoryCommand>() {

    private val log = Log(CreateCategoryCommandHandler::class.java)

    override fun handler(command: CreateCategoryCommand) {
        log.info("Received command with id ${command.id}")
        val categoryResult = Category.build {
            id = command.id
            name = command.name
        }

        log.info("Transforming command with id ${command.id} to result entity ${categoryResult}")
        when(categoryResult) {
            is Success -> categoryRepository.insert(categoryResult.entity)
            is Failure -> eventPublisher.publisher(DomainInvalidEvent(categoryResult.notifications))
        }
    }

}