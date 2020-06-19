package com.learning.ddd.application.query

import java.util.*


interface ProductQueryRepository {

    fun findAll(): List<ProductProjection>

    fun findById(id: UUID): Optional<ProductProjection>

}