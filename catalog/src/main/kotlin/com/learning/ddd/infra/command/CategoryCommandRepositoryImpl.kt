package com.learning.ddd.infra.command

import com.cross.extensions.firstOrOptional
import com.cross.extensions.getOrEmpty
import com.learning.ddd.application.domain.Category
import com.learning.ddd.application.domain.CategoryCommandRepository
import com.learning.ddd.infra.command.CategoryTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CategoryCommandRepositoryImpl : CategoryCommandRepository {

    override fun insert(category: Category) {
        transaction {
            CategoryTable.insert {
                it[id] = category.id
                it[name] = category.name
            }
        }
    }

    override fun findById(id: UUID): Optional<Category> {
        return transaction {
            CategoryTable.select {
                CategoryTable.id.eq(id)
            }.firstOrOptional().map {
                toCategory(it)
            }.getOrEmpty()
        }
    }

    private fun toCategory(row: ResultRow) = CategoryTable.rowToCategory(row)

}