package config

import com.typesafe.config.ConfigFactory

data class CoreAppSettings(
    val directory: String,
    val jsonDir: String,
    val appConfig: String,
)

data class CoreDatabaseSettings(
    val path: String,
)

data class CoreSettings(
    val app: CoreAppSettings,
    val database: CoreDatabaseSettings,
)

fun loadSettings(): CoreSettings {
    val config = ConfigFactory.load().getConfig("core")

    val app = config.getConfig("app")
    val db = config.getConfig("database")

    return CoreSettings(
        app = CoreAppSettings(
            directory = app.getString("directory"),
            jsonDir = app.getString("json-dir"),
            appConfig = app.getString("app-config")
        ),
        database = CoreDatabaseSettings(
            path = db.getString("path")
        )
    )
}
