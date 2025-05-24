package core.di

import core.data.CoreConfig
import core.db.DatabaseFactory
import core.db.dao.ConfigDao
import core.db.repository.ConnectionConfigRepository
import core.util.CoreConfigLoader
import org.koin.dsl.module

val appModule = module {
    single { CoreConfigLoader.appConfig }
    single { DatabaseFactory.init(get<CoreConfig>().database.path) }
    single { ConfigDao() }
    single { ConnectionConfigRepository(get()) }

}