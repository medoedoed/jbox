package core.database

import core.database.entity.ActiveConnection
import core.database.entity.ConnectionConfig
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

private val logger = KotlinLogging.logger {}

object DatabaseFactory {
    fun init(dbPath: String): Database {
        logger.info { "Connecting to database at path: $dbPath" }

        val db = Database.connect("jdbc:sqlite:$dbPath", driver = "org.sqlite.JDBC")

        transaction(db) {
            addLogger(StdOutSqlLogger)
            logger.info { "Creating schema if not exists..." }
            SchemaUtils.create(ConnectionConfig, ActiveConnection)
        }

        logger.info { "Database initialized successfully" }
        return db
    }
}
