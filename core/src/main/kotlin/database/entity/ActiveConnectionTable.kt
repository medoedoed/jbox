package core.database.entity

import org.jetbrains.exposed.sql.Table

object ActiveConnectionTable : Table("active_connection") {
    val connectionId = integer("connection_id")
}