package com.learning.ddd.application.domain

import com.cross.domain.Entity
import com.cross.domain.Notification
import com.cross.domain.ResultEntity
import com.cross.extensions.isNullOrBlank
import com.cross.extensions.validateSizeLessThan
import java.util.*

data class Category private constructor (val id: UUID = UUID.randomUUID(), val name: String) : Entity() {

    init {
        validate()
    }

    companion object {
        inline fun build(block: Category.Builder.() -> Unit) = Category.Builder().apply(block).build()
    }

    override fun validators(): List<Optional<Notification>> =
            listOf(
                name.isNullOrBlank(message = "Name is required."),
                name.validateSizeLessThan(length = 30, message = "Name should be less than 30 characters")
            )

    class Builder {
        var id: UUID = UUID.randomUUID()
        lateinit var name: String

        fun build() : ResultEntity<List<Notification>, Category> {
            val newCategory = Category(id = id, name =  name)

            return  when(newCategory.isValid()) {
                true -> ResultEntity.Success<Category>(newCategory)
                else -> ResultEntity.Failure(newCategory.notifications)
            }
        }
    }
}