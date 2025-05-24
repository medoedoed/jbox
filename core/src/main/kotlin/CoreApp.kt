package core

import core.di.appModule
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun main() {
    startKoin {
        modules(appModule)
    }

    val koin = GlobalContext.get()
    val server: CoreServer = koin.get()

    server.start()
    server.blockUntilShutdown()
}