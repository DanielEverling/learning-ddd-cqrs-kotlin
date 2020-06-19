package com.learning.ddd.infra.command

import org.jetbrains.exposed.sql.Table

object ProductTable : Table("product.product") {
    val id = uuid("id")
    val name = text("name")
    val description = text("description")
    val category = uuid("category_id").references(CategoryTable.id)
    val value = decimal("value", precision = 30, scale = 6)
    val active = bool("active")

    override val primaryKey = PrimaryKey(id)
}