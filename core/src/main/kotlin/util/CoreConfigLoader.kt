package core.util

import com.typesafe.config.ConfigFactory
import core.data.*

object CoreConfigLoader {
    val appConfig: CoreConfig by lazy {
        val config = ConfigFactory.load().getConfig("core")

        val grpc = config.getConfig("grpc")
        val app = config.getConfig("app")
        val db = config.getConfig("database")

        CoreConfig(
            grpc = GrpcConfig(
                host = grpc.getString("host"),
                port = grpc.getInt("port")
            ),
            app = AppConfig(
                directory = app.getString("directory"),
                jsonConfig = app.getString("json-config")
            ),
            database = DatabaseConfig(
                path = db.getString("path")
            )
        )
    }
}
