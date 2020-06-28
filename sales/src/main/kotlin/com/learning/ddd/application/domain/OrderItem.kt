package com.learning.ddd.application.domain

import com.cross.domain.ValidatorsAware
import com.cross.vo.Currency
import com.cross.vo.Quantity
import java.util.UUID

data class OrderItem(val product: UUID, val quantity: Quantity, val unitValue: Currency) : ValidatorsAware {

    val total: Currency
        get() = unitValue * quantity

    override fun validators() =
            listOf(
                quantity.valueBiggerThanZero("Quantity should be bigger than zero."),
                unitValue.valueBiggerThanZero("Unit value should be bigger than zero.")
        )

}
