package com.cross.extensions

fun ArrayList<String>.addWhenStringHasValue(value : String) {
    if (value.isNullOrBlank()) {
        return
    }
    this.add(value)
}