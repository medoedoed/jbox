package core

import core.di.appModule
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }

    val server = CoreServer()
    server.start()
    server.blockUntilShutdown()
}