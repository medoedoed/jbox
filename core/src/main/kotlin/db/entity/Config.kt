package core.db.entity

import org.jetbrains.exposed.dao.id.IntIdTable

object Config : IntIdTable() {
    val name = varchar("name", 30)
    val jsonPath = text("json_path")
}