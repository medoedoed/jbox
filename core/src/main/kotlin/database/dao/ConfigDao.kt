package core.database.dao

import core.database.entity.ConnectionConfig
import core.database.dto.ConfigDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class ConfigDao {
    fun all(): List<ConfigDto> = transaction {
        ConnectionConfig.selectAll().map {
            ConfigDto(
                id = it[ConnectionConfig.id].value,
                name = it[ConnectionConfig.name],
                jsonPath = it[ConnectionConfig.jsonPath]
            )
        }
    }

    fun insert(name: String, configJson: String): Int = transaction {
        ConnectionConfig.insert {
            it[ConnectionConfig.name] = name
            it[ConnectionConfig.jsonPath] = configJson
        }[ConnectionConfig.id].value
    }

    fun delete(id: Int): Int = transaction {
        ConnectionConfig.deleteWhere { ConnectionConfig.id eq id }
    }
}
