package core.db.dao

import core.db.entity.Config
import core.db.dto.ConfigDto
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class ConfigDao {
    fun all(): List<ConfigDto> = transaction {
        Config.selectAll().map {
            ConfigDto(
                id = it[Config.id].value,
                name = it[Config.name],
                jsonPath = it[Config.jsonPath]
            )
        }
    }

    fun insert(name: String, configJson: String): Int = transaction {
        Config.insert {
            it[Config.name] = name
            it[Config.jsonPath] = configJson
        }[Config.id].value
    }

    fun delete(id: Int): Int = transaction {
        Config.deleteWhere { Config.id eq id }
    }
}
