package config.data
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val grpc: GrpcConfig = GrpcConfig(),
    val logging: LoggingConfig = LoggingConfig(CliConfig(), CoreConfig()),
    val features: FeaturesConfig = FeaturesConfig(),
    val singBox: SingBoxConfig = SingBoxConfig(),
)

@Serializable
data class GrpcConfig(val host: String = "127.0.0.1", val port: Int = 3628)

@Serializable
data class LoggingConfig(val cli: CliConfig, val core: CoreConfig)

@Serializable
data class CliConfig(val level: String = "info", val file: String = "cli.logs/cli.log")

@Serializable
data class CoreConfig(val level: String = "info", val file: String = "cli.logs/core.log")


@Serializable
data class FeaturesConfig(val autostart: Boolean = false, val debug: Boolean = false)

@Serializable
data class SingBoxConfig(val port: Int = 1080, val logLevel: String = "info")

fun defaultAppConfig(): AppConfig = AppConfig()