package core

import core.data.CoreConfig
import core.db.repository.ConfigRepository
import core.service.ConfigService
import io.grpc.Server
import io.grpc.ServerBuilder
import io.grpc.protobuf.services.ProtoReflectionService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CoreServer() : KoinComponent {
    private val config: CoreConfig by inject()
    private val repository: ConfigRepository by inject()
    private val port = config.grpc.port

    private val server: Server = ServerBuilder
        .forPort(port)
        .addService(ConfigService(repository))
        .addService(ProtoReflectionService.newInstance())
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
