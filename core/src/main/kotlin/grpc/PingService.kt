package core.grpc

import core.ping.Empty
import core.ping.PingServiceGrpcKt
import core.ping.Pong

class PingService : PingServiceGrpcKt.PingServiceCoroutineImplBase() {
    override suspend fun ping(request: Empty): Pong {
        return Pong.newBuilder().setAnswer("OK").build()
    }
}