package com.learning.ddd.application.domain

import com.cross.domain.Notification
import com.cross.domain.ValidatorsAware
import com.cross.extensions.isNullOrBlank
import com.cross.extensions.validateSizeLessThan
import java.util.Optional

data class DeliveryAddress (val street: String, val number: Int, val city: String, val state: String, val complement: String) : ValidatorsAware {

    override fun validators(): List<Optional<Notification>> =
        listOf(
            street.isNullOrBlank(message = "Street is required."),
            street.validateSizeLessThan(length = 50, message = "Street should be less than 50 characters"),
            city.isNullOrBlank(message = "City is required."),
            city.validateSizeLessThan(length = 50, message = "City should be less than 20 characters"),
            state.isNullOrBlank(message = "State is required."),
            state.validateSizeLessThan(length = 2, message = "State must be 2 characters.")
        )

}
