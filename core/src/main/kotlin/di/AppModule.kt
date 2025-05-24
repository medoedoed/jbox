package core.di

import config.CoreSettings
import config.loadSettings
import config.data.AppConfig
import core.db.DatabaseFactory
import core.db.dao.ConfigDao
import core.db.repository.ConnectionConfigRepository
import org.koin.dsl.module
import java.nio.file.Path
import config.loadAppConfig
import core.util.provideAppConfig

val appModule = module {
    single<CoreSettings> { loadSettings() }
    single<AppConfig> { provideAppConfig(get()) }
    single { DatabaseFactory.init(get<CoreSettings>().database.path) }
    single { ConfigDao() }
    single { ConnectionConfigRepository(get()) }

}