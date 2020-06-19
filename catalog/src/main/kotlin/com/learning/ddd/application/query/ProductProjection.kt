package com.learning.ddd.application.query

import com.cross.extensions.format
import com.cross.vo.Currency
import com.learning.ddd.infra.command.CategoryTable
import com.learning.ddd.infra.command.ProductTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class ProductProjection (val id: UUID,
                              val name: String,
                              val description: String,
                              val value: String,
                              val category: CategoryProjection) {

    companion object {
        fun rowToProductProjection(row: ResultRow): ProductProjection =
                ProductProjection(
                        id = row[ProductTable.id],
                        name = row[ProductTable.name],
                        description = row[ProductTable.description],
                        value = row[ProductTable.value].format(),
                        category = CategoryProjection(row[CategoryTable.id], row[CategoryTable.name]))
    }

}
