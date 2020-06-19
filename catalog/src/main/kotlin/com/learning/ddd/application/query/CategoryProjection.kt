package com.learning.ddd.application.query

import com.learning.ddd.infra.command.CategoryTable
import org.jetbrains.exposed.sql.ResultRow
import java.util.*

data class CategoryProjection(val id: UUID, val name: String) {

    companion object {
        fun rowToCategoryProjection(row: ResultRow)=
                CategoryProjection(id = row[CategoryTable.id], name = row[CategoryTable.name])
    }
}
