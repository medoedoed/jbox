package core

import config.data.AppConfig
import config.data.defaultAppConfig
import config.loadSettings
import config.saveAppConfig
import core.di.appModule
import org.koin.core.context.startKoin
import java.nio.file.Files
import kotlin.io.path.Path

fun main() {
    startKoin {
        modules(appModule)
    }
    
    val server = CoreServer()
    server.start()
    server.blockUntilShutdown()
}