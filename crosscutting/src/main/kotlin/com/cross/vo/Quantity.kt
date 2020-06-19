package com.cross.vo



class Quantity private constructor(private val _value : Double) {

    companion object {
        fun of(value : Double)  =  Quantity(_value = value)

        fun of(value : Int)  =  Quantity(_value = value.toDouble())

        inline fun zero() : Quantity = of(0)
    }

    val value: Double
        get() = _value

    operator fun minus(value: Double) = Quantity.of(value = _value.minus(value))

    operator fun minus(value: Int) = Quantity.Companion.of(value = _value.minus(value.toDouble()))

    operator fun minus(quantity: Quantity) = Quantity.Companion.of(value = _value.minus(quantity._value))

    operator fun plus(value: Double) = Quantity.Companion.of(value = _value.plus(value))

    operator fun plus(value: Int) = Quantity.Companion.of(value = _value.plus(value))

    operator fun plus(quantity: Quantity ) = Quantity.Companion.of(value = _value.plus(quantity._value))

}