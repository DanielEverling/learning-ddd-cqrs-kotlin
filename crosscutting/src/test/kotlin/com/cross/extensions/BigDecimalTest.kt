package com.cross.extensions

import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test
import java.math.BigDecimal

internal class BigDecimalTest {

    @Test
    fun `should format bigdecimal value`() {
        val value = BigDecimal.valueOf(1983.07).format()
        "1.983,07" `should be equal to`  value
    }
}