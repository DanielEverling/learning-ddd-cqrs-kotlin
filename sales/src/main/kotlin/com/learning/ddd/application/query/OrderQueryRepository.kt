package com.learning.ddd.application.query

import java.util.Optional
import java.util.UUID

interface OrderQueryRepository {

    fun findById(id: UUID): Optional<OrderProjection>

}