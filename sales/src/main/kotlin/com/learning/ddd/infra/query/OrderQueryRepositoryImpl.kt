package com.learning.ddd.infra.query

import com.cross.extensions.firstOrOptional
import com.learning.ddd.application.query.OrderItemProjection
import com.learning.ddd.application.query.OrderProjection
import com.learning.ddd.application.query.OrderQueryRepository
import com.learning.ddd.infra.command.OrderItemTable
import com.learning.ddd.infra.command.OrderTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.Optional
import java.util.UUID
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class OrderQueryRepositoryImpl : OrderQueryRepository {

    override fun findById(id: UUID): Optional<OrderProjection> {
        val items = mutableListOf<OrderItemProjection>()
        return transaction {
            OrderTable
                    .innerJoin(OrderItemTable)
                    .select { OrderTable.id.eq(id) }
                    .toList()
                    .map {
                        items.add(OrderItemProjection.rowToOrderItemProjection(it))
                        it
                    }.map {
                        OrderProjection.rowToOrderProjection(it, items)
                    }.firstOrOptional()
        }
    }
}