package com.learning.ddd.infra.query

import com.cross.extensions.firstOrOptional
import com.cross.extensions.getOrEmpty
import com.learning.ddd.application.query.ProductProjection
import com.learning.ddd.application.query.ProductQueryRepository
import com.learning.ddd.infra.command.CategoryTable
import com.learning.ddd.infra.command.ProductTable
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductQueryRepositoryImpl : ProductQueryRepository {

    override fun findAll(): List<ProductProjection> {
        return transaction {
            ProductTable.innerJoin(otherTable = CategoryTable)
                    .selectAll()
                    .map { ProductProjection.rowToProductProjection(it) }
        }
    }

    override fun findById(id: UUID): Optional<ProductProjection> {
        return transaction {
            ProductTable.innerJoin(otherTable = CategoryTable)
                    .select{
                        ProductTable.id.eq(id)
                    }.firstOrOptional().map {
                        ProductProjection.rowToProductProjection(it)
                    }.getOrEmpty()
        }
    }

}