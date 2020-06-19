package com.learning.ddd.application.domain

import com.cross.domain.ResultEntity.*
import com.cross.vo.Currency
import com.learning.ddd.application.builder.CategoryBuilder
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

internal class ProductTest {

    @Test
    fun `should create a valid product` () {

        val resultProduct = Product.build {
            name = "A name valid"
            description = "A description valid"
            value = Currency.of(amount = 100)
            category = CategoryBuilder.build()
        }

        when(resultProduct) {
            is Failure -> apply {
                fail { "Criation of a product should be with success" }
            }
            is Success -> apply {
                val product = resultProduct.entity
                product.id `should not be` null
                "A name valid" `should be equal to`  product.name
                "A description valid" `should be equal to` product.description
                Currency.of(amount = 100) `should be equal to`  product.value
                true `should be equal to` product.active
            }
        }
    }

    @Test
    fun `should validade creation of a product with empty fields`() {
        val resultProduct = Product.build {
            name = ""
            description = ""
            value = Currency.of(amount = -100)
            category = CategoryBuilder.build()
        }

        when(resultProduct) {
            is Success -> apply {
                fail { "Criation of a product should be with fail" }
            }
            is Failure -> apply {
                val notifications = resultProduct.notifications
                "Name is required." `should be equal to` notifications[0].notification
                "Description is required." `should be equal to` notifications[1].notification
                "Value should be bigger than zero." `should be equal to` notifications[2].notification
            }
        }
    }

    @Test
    fun `should validade creation of a product with invalid fields`() {
        val resultProduct = Product.build {
            name = RandomStringUtils.randomAlphanumeric(50)
            description = RandomStringUtils.randomAlphanumeric(200)
            value = Currency.of(amount = -100)
            category = CategoryBuilder.build()
        }

        when(resultProduct) {
            is Success -> apply {
                fail { "Criation of a product should be with fail" }
            }
            is Failure -> apply {
                val notifications = resultProduct.notifications
                "Name should be less than 30 characters" `should be equal to` notifications[0].notification
                "Description should be less than 100 characters" `should be equal to` notifications[1].notification
                "Value should be bigger than zero." `should be equal to` notifications[2].notification
            }
        }
    }

}

