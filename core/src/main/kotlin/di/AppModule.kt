package core.di

import config.CoreSettings
import config.loadSettings
import config.data.AppConfig
import core.database.DatabaseFactory
import core.database.dao.ConnectionConfigDao
import core.database.repository.ConnectionConfigRepository
import org.koin.dsl.module
import config.provideAppConfig
import core.CoreServer
import core.configuration.configureLogging
import core.grpc.ConnectionConfigGrpcService
import core.grpc.PingService
import core.util.JsonSaver
import core.util.UriParser

val appModule = module {
    single<CoreSettings> { loadSettings() }
    single<AppConfig> {
        val config = provideAppConfig(get())
        configureLogging(config.logging)
        config
    }

    single { DatabaseFactory.init(get<CoreSettings>().database.path) }
    single { ConnectionConfigDao() }
    single { ConnectionConfigRepository(get()) }
    single { CoreServer(get(), get(), get(), get()) }
    single { ConnectionConfigGrpcService(get(), get(), get(), get()) }
    single { PingService() }
    single { UriParser(get()) }
    single { JsonSaver() }

}