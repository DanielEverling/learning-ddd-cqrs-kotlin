package com.learning.ddd.infra.command

import com.learning.ddd.application.domain.Product
import com.learning.ddd.application.domain.ProductCommandRepository
import com.learning.ddd.infra.command.ProductTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class ProductCommandRepositoryImpl : ProductCommandRepository {

    override fun insert(product: Product) {
        transaction {
            ProductTable.insert {
                it[id] = product.id
                it[name] = product.name
                it[description] = product.description
                it[category] = product.category.id
                it[value] = product.value.amount
                it[active] = product.active
            }
        }
    }

}