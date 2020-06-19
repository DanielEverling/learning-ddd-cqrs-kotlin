package com.learning.ddd.infra.command

import com.learning.ddd.application.domain.Category
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

object CategoryTable : Table("product.category" ) {

    val id = uuid("id")
    val name = text("name")

    override val primaryKey = PrimaryKey(id)

    fun rowToCategory(row: ResultRow) : Category {
        return Category.build {
            id = row[CategoryTable.id]
            name = row[CategoryTable.name]
        }.entity()
    }

}