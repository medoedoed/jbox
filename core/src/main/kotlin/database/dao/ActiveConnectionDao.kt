package core.database.dao

import core.database.entity.ActiveConnectionTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

object ActiveConnectionDao {
    fun setActiveConnection(id: Int) {
        transaction {
            val row = ActiveConnectionTable.selectAll().limit(1).singleOrNull()
            if (row == null) {
                ActiveConnectionTable.insert {
                    it[connectionId] = id
                }
            } else {
                ActiveConnectionTable.update(
                    where = { ActiveConnectionTable.connectionId eq row[ActiveConnectionTable.connectionId] }
                ) {
                    it[connectionId] = id
                }
            }
        }
    }

    fun getActiveConnection(): Int? {
        return transaction {
            ActiveConnectionTable
                .selectAll()
                .limit(1)
                .singleOrNull()
                ?.get(ActiveConnectionTable.connectionId)
        }
    }
}
