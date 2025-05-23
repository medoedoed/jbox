package core.data

data class CoreConfig(
    val grpc: GrpcConfig,
    val app: AppConfig,
    val database: DatabaseConfig
)

data class GrpcConfig(
    val host: String,
    val port: Int
)

data class AppConfig(
    val directory: String,
    @JvmField val jsonConfig: String
)

data class DatabaseConfig(
    val path: String
)
