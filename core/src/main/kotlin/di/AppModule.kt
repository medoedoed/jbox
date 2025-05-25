package core.di

import config.CoreSettings
import config.loadSettings
import config.data.AppConfig
import core.database.DatabaseFactory
import core.database.dao.ConfigDao
import core.database.repository.ConnectionConfigRepository
import org.koin.dsl.module
import config.provideAppConfig
import core.CoreServer
import core.configuration.configureLogging
import core.service.ConnectionConfigService
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
    single { ConfigDao() }
    single { ConnectionConfigRepository(get()) }
    single { CoreServer(get(), get(), get(), get()) }
    single { ConnectionConfigService(get(), get(), get(), get()) }
    single { UriParser(get()) }
    single { JsonSaver() }

}