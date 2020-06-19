package com.learning.ddd.infra.query

import com.cross.extensions.firstOrOptional
import com.cross.extensions.getOrEmpty
import com.learning.ddd.application.query.CategoryProjection
import com.learning.ddd.application.query.CategoryQueryRepository
import com.learning.ddd.infra.command.CategoryTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CategoryQueryRepositoryImpl : CategoryQueryRepository {

    override fun findAll(): List<CategoryProjection> {
        return transaction {
            CategoryTable
                    .selectAll()
                    .map {
                        CategoryProjection.rowToCategoryProjection(it)
                    }
        }
    }

    override fun findById(id: UUID): Optional<CategoryProjection> {
        return transaction {
            CategoryTable.select{
                CategoryTable.id.eq(id)
            }.firstOrOptional().map {
                CategoryProjection.rowToCategoryProjection(it)
            }.getOrEmpty()
        }
    }

}