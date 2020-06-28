package com.learning.ddd.application.domain

import java.util.Optional
import java.util.UUID

interface OrderCommandRepository {

    fun insert(order: Order)

    fun findById(id: UUID): Optional<Order>

}