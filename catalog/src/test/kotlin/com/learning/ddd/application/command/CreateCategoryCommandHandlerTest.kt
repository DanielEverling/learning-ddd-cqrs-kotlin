package com.learning.ddd.application.command

import com.cross.events.EventPublisher
import com.learning.ddd.application.commons.anyDomainInvalidEvent
import com.learning.ddd.application.domain.Category
import com.learning.ddd.application.domain.CategoryCommandRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test

fun anyCategory() : Category = any()

internal class CreateCategoryCommandHandlerTest {

    @Test
    fun `should proccessing create category command with success` () {
        val categoryCommandRepository : CategoryCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val createProductCommandHandler = CreateCategoryCommandHandler(categoryRepository = categoryCommandRepository)
        createProductCommandHandler.eventPublisher = eventPublisher

        createProductCommandHandler.handler(CreateCategoryCommand(name = "category"))

        verify(categoryCommandRepository, times(1)).insert(anyCategory())
        verify(eventPublisher, never()).publisher(anyDomainInvalidEvent())
    }

    @Test
    fun `should throw domain event when there is fail in create category command` () {
        val categoryCommandRepository : CategoryCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val createProductCommandHandler = CreateCategoryCommandHandler(categoryRepository = categoryCommandRepository)
        createProductCommandHandler.eventPublisher = eventPublisher

        createProductCommandHandler.handler(CreateCategoryCommand(name = ""))

        verify(eventPublisher, times(1)).publisher(anyDomainInvalidEvent())
        verify(categoryCommandRepository, never()).insert(anyCategory())
    }

}