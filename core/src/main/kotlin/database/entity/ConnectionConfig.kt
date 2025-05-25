package core.database.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object ConnectionConfig : IntIdTable() {
    val name = varchar("name", 30)
    val jsonPath = text("json_path")
}