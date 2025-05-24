package config.data
import kotlinx.serialization.Serializable

@Serializable
data class AppConfig(
    val grpc: GrpcConfig = GrpcConfig(),
    val logging: LoggingConfig = LoggingConfig(),
    val features: FeaturesConfig = FeaturesConfig()
)

@Serializable
data class GrpcConfig(val host: String = "127.0.0.1", val port: Int = 3628)

@Serializable
data class LoggingConfig(val level: String = "info", val file: String = "logs/jbox.log")

@Serializable
data class FeaturesConfig(val autostart: Boolean = false, val debug: Boolean = false)

fun defaultAppConfig(): AppConfig = AppConfig()