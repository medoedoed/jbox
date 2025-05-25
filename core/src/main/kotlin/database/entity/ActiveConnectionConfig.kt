package core.database.entity

import org.jetbrains.exposed.sql.Table

object ActiveConnection : Table("active_connection") {
    val connectionId = integer("connection_id")
}