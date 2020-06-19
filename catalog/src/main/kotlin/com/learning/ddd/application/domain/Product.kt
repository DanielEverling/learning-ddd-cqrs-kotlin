package com.learning.ddd.application.domain

import com.cross.domain.Entity
import com.cross.domain.Notification
import com.cross.domain.ResultEntity
import com.cross.extensions.isNullOrBlank
import com.cross.extensions.validateSizeLessThan
import com.cross.vo.Currency
import com.cross.vo.Quantity
import java.time.LocalDateTime
import java.util.*

data class Product(val id: UUID = UUID.randomUUID(),
                   val name: String,
                   val description: String,
                   val value: Currency,
                   val category: Category) : Entity() {

    val active : Boolean = true

    init {
        validate()
    }

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    override fun validators(): List<Optional<Notification>> =
        listOf(
                name.isNullOrBlank(message = "Name is required."),
                name.validateSizeLessThan(length = 30, message = "Name should be less than 30 characters"),
                description.isNullOrBlank(message = "Description is required."),
                description.validateSizeLessThan(length = 100, message = "Description should be less than 100 characters"),
                value.valueBiggerThanZero("Value should be bigger than zero.")
        )

    class Builder {
        var id: UUID = UUID.randomUUID()
        lateinit var name: String
        lateinit var description: String
        lateinit var value: Currency
        lateinit var category: Category

        fun build() : ResultEntity<List<Notification>, Product> {
            val newProduct = Product(id = id, name =  name, description = description, value = value, category = category)

            return  when(newProduct.isValid()) {
                true -> ResultEntity.Success<Product>(newProduct)
                else -> ResultEntity.Failure(newProduct.notifications)
            }
        }
    }

}