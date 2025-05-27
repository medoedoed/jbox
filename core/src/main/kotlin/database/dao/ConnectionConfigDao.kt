package core.database.dao

import core.database.dto.ConfigDto
import core.database.entity.ConnectionConfigTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction


class ConnectionConfigDao {
    fun all(): List<ConfigDto> = transaction {
        ConnectionConfigTable.selectAll().map {
            ConfigDto(
                id = it[ConnectionConfigTable.id],
                name = it[ConnectionConfigTable.name],
                jsonPath = it[ConnectionConfigTable.jsonPath]
            )
        }
    }

    fun select(id: Int): ConfigDto? = transaction {
        ConnectionConfigTable.selectAll().where { ConnectionConfigTable.id eq id }
         .map {
                ConfigDto(
                    id = it[ConnectionConfigTable.id],
                    name = it[ConnectionConfigTable.name],
                    jsonPath = it[ConnectionConfigTable.jsonPath]
                )
            }
//            .firstOrNull()
            .singleOrNull()
    }

    fun insert(name: String, jsonPath: String): Int = transaction {
        ConnectionConfigTable.insert {
            it[ConnectionConfigTable.name] = name
            it[ConnectionConfigTable.jsonPath] = jsonPath
        }[ConnectionConfigTable.id]
    }

    fun delete(id: Int): Int = transaction {
        ConnectionConfigTable.deleteWhere { ConnectionConfigTable.id eq id }
    }

    fun exists(id: Int): Boolean = transaction {
        ConnectionConfigTable.selectAll().where { ConnectionConfigTable.id eq id }
            .count() > 0
    }

    fun update(id: Int, name: String?, jsonPath: String?): Int = transaction {
        ConnectionConfigTable.update({ ConnectionConfigTable.id eq id }) {
            if (name != null) it[ConnectionConfigTable.name] = name
            if (jsonPath != null) it[ConnectionConfigTable.jsonPath] = jsonPath
        }
    }
}
