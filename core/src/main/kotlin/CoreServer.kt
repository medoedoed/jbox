package core

import config.CoreSettings
import config.data.AppConfig
import core.database.repository.ConnectionConfigRepository
import core.service.ConnectionConfigService
import core.service.PingService
import io.github.oshai.kotlinlogging.KotlinLogging
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService

class CoreServer(
    repository: ConnectionConfigRepository,
    config: AppConfig,
    settings: CoreSettings,
    connectionConfigService: ConnectionConfigService
) {
    private val logger = KotlinLogging.logger {}
    private val port = config.grpc.port

    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(connectionConfigService)
        .addService(PingService())
        .addService(ProtoReflectionService.newInstance()) // temp for testing via grpcurl
        .build()

    fun start() {
        server.start()
        logger.info { "Server started, listening on $port" }

        Runtime.getRuntime().addShutdownHook(Thread {
            logger.info { "Shutting down gracefully" }
            stop()
        })
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
