package core.util

import config.CoreSettings
import config.data.AppConfig
import config.data.defaultAppConfig
import config.loadAppConfig
import config.saveAppConfig
import java.nio.file.Files
import java.nio.file.Path

fun provideAppConfig(settings: CoreSettings): AppConfig {
    val path = Path.of(settings.app.appConfig)
    if (!Files.exists(path)) {
        saveAppConfig(path, defaultAppConfig())
    }
    return loadAppConfig(path)
}