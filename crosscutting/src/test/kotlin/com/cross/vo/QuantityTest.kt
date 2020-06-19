package com.cross.vo

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

internal class QuantityTest {

    @TestFactory
    fun `create quantity type`() = listOf(
            DynamicTest.dynamicTest("should create a quantity type with int") {
                Quantity.of(5).value `should be equal to` 5.0
            },
            DynamicTest.dynamicTest("should create a quantity type with double") {
                Quantity.of(5.0).value `should be equal to` 5.0
            }
    )

    @TestFactory
    fun `should subtract values`() = listOf(
            DynamicTest.dynamicTest("should subtract values with double") {
                val valueToSubtract =  Quantity.of(1000)
                (valueToSubtract - 90.00).value  `should be equal to` 910.00
            },
            DynamicTest.dynamicTest("should subtract values with int quantity") {
                val valueToSubtract =  Quantity.of(2000)
                (valueToSubtract - Quantity.of(100)).value  `should be equal to` 1900.00
            },
            DynamicTest.dynamicTest("should subtract values with int int") {
                val valueToSubtract =  Quantity.of(0)
                (valueToSubtract - 100).value  `should be equal to` -100.00
            }
    )

    @TestFactory
    fun `should plus values`() = listOf(
            DynamicTest.dynamicTest("should plus values with double") {
                val valueToPlus =  Quantity.of(1000)
                (valueToPlus + 90.00).value  `should be equal to` 1090.00
            },
            DynamicTest.dynamicTest("should plus values with int quantity") {
                val valueToPlus =  Quantity.of(2000)
                (valueToPlus + Quantity.of(100)).value  `should be equal to` 2100.00
            },
            DynamicTest.dynamicTest("should plus values with int int") {
                val valueToPlus =  Quantity.of(0)
                (valueToPlus + 100).value  `should be equal to` 100.00
            }
    )

    @Test
    fun `should create a currency with zero value`() {
        val currency = Quantity.zero()
        currency.value `should be equal to` 0.0
    }

}

