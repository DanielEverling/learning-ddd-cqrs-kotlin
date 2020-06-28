package com.cross.extensions

import com.cross.vo.Currency
import org.jetbrains.exposed.sql.ResultRow

public inline fun <T> Iterable<T>.sumByCurrency(selector: (T) -> Currency): Currency {
    var sum: Currency = Currency.zero()
    this.forEach {
        sum += selector(it)
    }
    return sum
}