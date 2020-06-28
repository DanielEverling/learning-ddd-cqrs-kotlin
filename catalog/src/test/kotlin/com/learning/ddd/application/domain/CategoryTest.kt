package com.learning.ddd.application.domain

import com.cross.domain.ResultEntity
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should not be`
import org.apache.commons.lang3.RandomStringUtils
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail

internal class CategoryTest {

    @Test
    fun `should create a valid category` () {

        val resultCategory = Category.build {
            name = "A name valid"
        }

        when(resultCategory) {
            is ResultEntity.Failure -> apply {
                fail { "Criation of a category should be with success" }
            }
            is ResultEntity.Success -> apply {
                val category = resultCategory.entity
                category.id `should not be` null
                category.name `should be equal to` "A name valid"
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
                fail { "Criation of a category should be with fail" }
            }
            is ResultEntity.Failure -> apply {
                val notifications = resultCategory.notifications
                notifications[0].notification `should be equal to` "Name is required."
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
                fail { "Criation of a category should be with fail" }
            }
            is ResultEntity.Failure -> apply {
                val notifications = resultCategory.notifications
                notifications[0].notification `should be equal to` "Name should be less than 30 characters"
            }
        }
    }
}