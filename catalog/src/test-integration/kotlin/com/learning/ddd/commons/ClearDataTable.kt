package com.learning.ddd.commons

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

object ClearDataTable {

    fun clearAllTables() {
        val connection = createConnection()
        connection.autoCommit = false
        val query = connection.createStatement()
        Commands.values().forEach {
            query.execute(it.commandSql)
        }
        connection.commit()
        connection.close()
    }

    private fun createConnection(): Connection {
        val props = Properties()
        val baseUrl = getEnv("DB_HOST", "jdbc:postgresql://localhost:5432/ecommerce")
        props.setProperty("user", "postgres")
        props.setProperty("password", "postgres")
        return DriverManager.getConnection(baseUrl, props)
    }

    enum class Commands(val commandSql: String) {
        TRUNCATE_CATEGORY("truncate product.category cascade"),
        TRUNCATE_PRODUCT("truncate product.product cascade"),
    }

}
