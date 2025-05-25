package core.database.dao

import core.database.entity.ActiveConnection
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object ActiveConnectionDao {
    fun setActiveConnection(id: Int) {
        transaction {
            val row = ActiveConnection.selectAll().limit(1).singleOrNull()
            if (row == null) {
                ActiveConnection.insert {
                    it[connectionId] = id
                }
            } else {
                ActiveConnection.update(
                    where = { ActiveConnection.connectionId eq row[ActiveConnection.connectionId] }
                ) {
                    it[connectionId] = id
                }
            }
        }
    }

    fun getActiveConnection(): Int? {
        return transaction {
            ActiveConnection
                .selectAll()
                .limit(1)
                .singleOrNull()
                ?.get(ActiveConnection.connectionId)
        }
    }
}
