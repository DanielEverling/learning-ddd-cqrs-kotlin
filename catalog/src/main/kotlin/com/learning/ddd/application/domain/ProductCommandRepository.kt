package com.learning.ddd.application.domain


interface ProductCommandRepository {

    fun insert(product : Product)

}
