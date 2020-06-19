package com.learning.ddd.application.domain

import java.util.*

interface CategoryCommandRepository {

    fun insert(category: Category)

    fun findById(category: UUID): Optional<Category>

}
