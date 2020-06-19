package com.learning.ddd

import io.quarkus.runtime.Quarkus

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        Quarkus.run(*args)
    }
}