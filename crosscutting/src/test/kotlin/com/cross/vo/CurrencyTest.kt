package com.cross.vo

import com.cross.vo.CurrencyType.BRL
import org.amshove.kluent.`should be equal to`
import java.math.BigDecimal
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class CurrencyTest {

    @TestFactory
    fun `create currency type `() = listOf(
        DynamicTest.dynamicTest("should create a currency type with int") {
            val withoutCurrency = createCurrency(null, 1983)
            val withCurrency = createCurrency(BRL, 1983)

            validateCurrency(withoutCurrency, BigDecimal(1983), BRL)
            validateCurrency(withCurrency, BigDecimal(1983), BRL)
        },
        DynamicTest.dynamicTest("should create a currency type with double") {
            val withoutCurrency = createCurrency(null, 5123.98)
            val withCurrency = createCurrency(BRL, 5123.98)

            validateCurrency(withoutCurrency, BigDecimal(5123.98), BRL)
            validateCurrency(withCurrency, BigDecimal(5123.98), BRL)
        },
        DynamicTest.dynamicTest("should create a currency type with big decimal") {
            val withoutCurrency = createCurrency(null, BigDecimal(67.2))
            val withCurrency = createCurrency(BRL, BigDecimal(67.2))

            validateCurrency(withoutCurrency, BigDecimal(67.2), BRL)
            validateCurrency(withCurrency, BigDecimal(67.2), BRL)
        }
    )

    @TestFactory
    fun `should subtract values`() = listOf(
        DynamicTest.dynamicTest("should subtract values with double") {
            (Currency.of(amount = 10.00) - 15.55).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        },
        DynamicTest.dynamicTest("should subtract values with int big decimal") {
            (Currency.of(amount = 10.00) - BigDecimal(15.55)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        },
        DynamicTest.dynamicTest("should subtract values with int currency") {
            (Currency.of(amount = 10.00) - Currency.of(amount = 15.55)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(-5.55)).amountFormatted
        }
    )

    @TestFactory
    fun `should multiply values`() = listOf(
            DynamicTest.dynamicTest("should multiply values with double") {
                (Currency.of(amount = 10.00) * 10).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(100)).amountFormatted
            },
            DynamicTest.dynamicTest("should multiply values with int big decimal") {
                (Currency.of(amount = 10.00) * Quantity.of(12)).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(120)).amountFormatted
            },
            DynamicTest.dynamicTest("should multiply values with int quantity") {
                (Currency.of(amount = 10.00) * 5.toDouble()).amountFormatted `should be equal to` Currency.of(amount = BigDecimal(50)).amountFormatted
            }
    )

    @Test
    fun `should create a currency with zero value`() {
        val currency = Currency.zero()
        currency.amount `should be equal to` BigDecimal.ZERO
    }

    private fun createCurrency(_currency: CurrencyType?, _amount: Int): Currency {
        return _currency?.let {
            Currency.of(currency = it, amount = _amount)
        } ?: Currency.of(amount = _amount)
    }

    private fun createCurrency(_currency: CurrencyType?, _amount: Double): Currency {
        return _currency?.let {
            Currency.of(currency = it, amount = _amount)
        } ?: Currency.of(amount = _amount)
    }

    private fun createCurrency(_currency: CurrencyType?, _amount: BigDecimal): Currency {
        return _currency?.let {
            Currency.of(currency = it, amount = _amount)
        } ?: Currency.of(amount = _amount)
    }

    private fun validateCurrency(currencyToValidate: Currency, numberShouldBeEqual: Number, currencyTypeShouldBeEqual: CurrencyType) {
        currencyToValidate.amount `should be equal to` numberShouldBeEqual
        currencyToValidate.currency `should be equal to` currencyTypeShouldBeEqual
    }
}
