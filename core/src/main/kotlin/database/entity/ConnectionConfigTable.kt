package core.database.entity

import org.jetbrains.exposed.sql.Table

object ConnectionConfigTable : Table("connection_config") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 30).uniqueIndex()
    val jsonPath = text("json_path")

    override val primaryKey = PrimaryKey(id)
}