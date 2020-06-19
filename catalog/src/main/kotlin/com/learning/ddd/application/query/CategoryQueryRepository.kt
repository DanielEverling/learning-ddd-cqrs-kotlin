package com.learning.ddd.application.query

import java.util.*

interface CategoryQueryRepository {

    fun findById(id: UUID): Optional<CategoryProjection>

    fun findAll(): List<CategoryProjection>

}