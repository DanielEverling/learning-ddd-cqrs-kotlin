package com.cross.vo

import com.cross.domain.Notification
import com.cross.extensions.format
import com.cross.vo.CurrencyType.BRL
import java.math.BigDecimal
import java.util.*

enum class CurrencyType {
    BRL
}

data class Currency constructor(private val _currency: CurrencyType, private val _amount: BigDecimal) {

    companion object {
        inline fun of(amount: BigDecimal, currency: CurrencyType = BRL) = Currency(_currency = currency, _amount = amount)

        inline fun of(amount: Double, currency: CurrencyType = BRL) = Currency(_currency = currency, _amount = BigDecimal(amount))

        inline fun of(amount: Int, currency: CurrencyType = BRL) = Currency(_currency = currency, _amount = BigDecimal(amount))

        inline fun zero(currency: CurrencyType = BRL) = Currency(_currency = currency, _amount = BigDecimal.ZERO)

    }

    operator fun times(amount: Double) = of(amount = _amount.multiply(BigDecimal(amount)))

    operator fun times(amount: Int) = of(amount = _amount.multiply(BigDecimal(amount)))

    operator fun times(amount: Quantity) = of(amount = _amount.multiply(BigDecimal(amount.value)))

    operator fun minus(amount: Double) = of(amount = _amount.subtract(BigDecimal(amount)))

    operator fun minus(amount: BigDecimal) = of(amount = _amount.subtract(amount))

    operator fun minus(currency: Currency) = of(amount = _amount.subtract(currency._amount))

    operator fun plus(amount: Double) = of(amount = _amount.plus(BigDecimal(amount)))

    operator fun plus(amount: BigDecimal) = of(amount = _amount.plus(amount))

    operator fun plus(currency: Currency) = of(amount = _amount.plus(currency._amount))

    val amount: BigDecimal
        get() = _amount

    val amountFormatted: String
        get() = amount.format()

    val currency: CurrencyType
        get() = _currency

    fun valueBiggerThanZero(message : String) : Optional<Notification> {
        return when (_amount > BigDecimal.ZERO) {
            true -> Optional.empty()
            else -> Optional.of(Notification(notification = message))
        }
    }
}
