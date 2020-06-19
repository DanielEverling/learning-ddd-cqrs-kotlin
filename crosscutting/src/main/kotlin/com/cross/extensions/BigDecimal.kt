package com.cross.extensions

import java.math.BigDecimal
import java.text.DecimalFormat

import java.text.DecimalFormatSymbols
import java.util.Locale

private val SYMBOL = DecimalFormatSymbols(Locale("pt", "BR"))
fun BigDecimal.format(): String = DecimalFormat("#,###,##0.00", SYMBOL).format(this.toDouble())