package com.learning.ddd.application.domain

import com.cross.domain.ResultEntity
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test

internal class CategoryTest {

    @Test
    fun `should create a valid category` () {

        val resultCategory = Category.build {
            name = "A name valid"
        }

        when(resultCategory) {
            is ResultEntity.Failure -> apply {
                org.junit.jupiter.api.fail { "Criation of a category should be with success" }
            }
            is ResultEntity.Success -> apply {
                val category = resultCategory.entity
                category.id `should not be` null
                "A name valid" `should be equal to`  category.name
            }
        }
    }

    @Test
    fun `should validade creation of a category with empty fields`() {
        val resultCategory = Category.build {
            name = ""
        }

        when(resultCategory) {
            is ResultEntity.Success -> apply {
                org.junit.jupiter.api.fail { "Criation of a category should be with fail" }
            }
            is ResultEntity.Failure -> apply {
                val notifications = resultCategory.notifications
                "Name is required." `should be equal to` notifications[0].notification
            }
        }
    }

    @Test
    fun `should validade creation of a category with invalid fields`() {
        val resultCategory = Category.build {
            name = RandomStringUtils.randomAlphanumeric(50)
        }

        when(resultCategory) {
            is ResultEntity.Success -> apply {
                org.junit.jupiter.api.fail { "Criation of a category should be with fail" }
            }
            is ResultEntity.Failure -> apply {
                val notifications = resultCategory.notifications
                "Name should be less than 30 characters" `should be equal to` notifications[0].notification
            }
        }
    }
}