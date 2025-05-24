package core

import config.CoreSettings
import config.data.AppConfig
import core.db.repository.ConnectionConfigRepository
import core.service.ConnectionConfigService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CoreServer : KoinComponent {
    private val repository: ConnectionConfigRepository by inject()
    private val config: AppConfig by inject()
    private val port = config.grpc.port

    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(ConnectionConfigService(repository))
        .addService(ProtoReflectionService.newInstance()) // temp for testing via grpcurl
        .build()

    fun start() {
        server.start()
        println("gRPC is working on port $port")
        Runtime.getRuntime().addShutdownHook(Thread {
            println("server shutting down")
            this@CoreServer.stop()
        })
    }

    fun stop() {
        server.shutdown()
    }

    fun blockUntilShutdown() {
        server.awaitTermination()
    }
}
