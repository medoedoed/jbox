package cli.service;

import core.ping.Empty;
import core.ping.PingServiceGrpc;
import core.ping.Pong;

import javax.inject.Inject;

public class GrpcPingService {
    private final PingServiceGrpc.PingServiceBlockingStub stub;

    @Inject
    public GrpcPingService(PingServiceGrpc.PingServiceBlockingStub stub) {
        this.stub = stub;
    }

    public Pong getPing() {
        return stub.ping(Empty.newBuilder().build());
    }
}
