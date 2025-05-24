package config

import com.typesafe.config.ConfigFactory

data class CoreAppConfig(
    val directory: String,
    val jsonDir: String,
    val appConfig: String,
)

data class CoreDatabaseConfig(
    val path: String,
)

data class CoreConfig(
    val app: CoreAppConfig,
    val database: CoreDatabaseConfig,
)

fun loadConfig(): CoreConfig {
    val config = ConfigFactory.load().getConfig("core")

    val app = config.getConfig("app")
    val db = config.getConfig("database")

    return CoreConfig(
        app = CoreAppConfig(
            directory = app.getString("directory"),
            jsonDir = app.getString("json-dir"),
            appConfig = app.getString("app-config")
        ),
        database = CoreDatabaseConfig(
            path = db.getString("path")
        )
    )
}
