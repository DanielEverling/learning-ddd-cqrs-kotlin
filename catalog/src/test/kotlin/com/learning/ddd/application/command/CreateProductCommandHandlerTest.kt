package com.learning.ddd.application.command

import com.cross.events.EventPublisher
import com.learning.ddd.application.builder.CategoryBuilder
import com.learning.ddd.application.commons.anyDomainInvalidEvent
import com.learning.ddd.application.commons.anyEntityNotFoundEvent
import com.learning.ddd.application.commons.anyUUID

import com.learning.ddd.application.domain.CategoryCommandRepository
import com.learning.ddd.application.domain.Product
import com.learning.ddd.application.domain.ProductCommandRepository
import com.nhaarman.mockitokotlin2.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.*

fun anyProduct() : Product = any()

internal class CreateProductCommandHandlerTest {

    @Test
    fun `should proccessing create category command with success` () {
        val categoryCommandRepository : CategoryCommandRepository = mock()
        val productCommandRepository : ProductCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val category = CategoryBuilder.build()

        `whenever`(categoryCommandRepository.findById(anyUUID())).thenReturn(Optional.of(category))
        val createProductCommandHandler = CreateProductCommandHandler(categoryRepository = categoryCommandRepository, productRepository = productCommandRepository)
        createProductCommandHandler.eventPublisher = eventPublisher

        createProductCommandHandler.handler(
                CreateProductCommand(
                name = "category",
                category = UUID.randomUUID(),
                value = BigDecimal.TEN,
                description = "description"))

        verify(categoryCommandRepository, times(1)).findById(anyUUID())
        verify(productCommandRepository, times(1)).insert(anyProduct())
        verify(eventPublisher, never()).publisher(anyDomainInvalidEvent())
    }

    @Test
    fun `should throw domain event when not found category by id` () {
        val categoryCommandRepository : CategoryCommandRepository = mock()
        val productCommandRepository : ProductCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()

        `whenever`(categoryCommandRepository.findById(anyUUID())).thenReturn(Optional.empty())
        val createProductCommandHandler = CreateProductCommandHandler(categoryRepository = categoryCommandRepository, productRepository = productCommandRepository)
        createProductCommandHandler.eventPublisher = eventPublisher

        createProductCommandHandler.handler(
                CreateProductCommand(
                        name = "category",
                        category = UUID.randomUUID(),
                        value = BigDecimal.TEN,
                        description = "description"))

        verify(categoryCommandRepository, times(1)).findById(anyUUID())
        verify(eventPublisher, times(1)).publisher(anyEntityNotFoundEvent())
        verify(productCommandRepository, never()).insert(anyProduct())
    }

    @Test
    fun `should throw domain event when command there is wrong information` () {
        val categoryCommandRepository : CategoryCommandRepository = mock()
        val productCommandRepository : ProductCommandRepository = mock()
        val eventPublisher : EventPublisher = mock()
        val category = CategoryBuilder.build()

        `whenever`(categoryCommandRepository.findById(anyUUID())).thenReturn(Optional.of(category))
        val createProductCommandHandler = CreateProductCommandHandler(categoryRepository = categoryCommandRepository, productRepository = productCommandRepository)
        createProductCommandHandler.eventPublisher = eventPublisher

        createProductCommandHandler.handler(
                CreateProductCommand(
                        name = "",
                        category = UUID.randomUUID(),
                        value = BigDecimal.ZERO,
                        description = ""))

        verify(categoryCommandRepository, times(1)).findById(anyUUID())
        verify(eventPublisher, times(1)).publisher(anyDomainInvalidEvent())
        verify(productCommandRepository, never()).insert(anyProduct())
    }

}