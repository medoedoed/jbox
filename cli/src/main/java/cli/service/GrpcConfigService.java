package cli.service;

import core.grpc.ConfigList;
import core.grpc.ConnectionConfigServiceGrpc;
import core.grpc.Empty;

import javax.inject.Inject;

public class GrpcConfigService
        extends ConnectionConfigServiceGrpc.ConnectionConfigServiceImplBase {
    private final ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub;

    @Inject
    public GrpcConfigService(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub) {
        this.stub = stub;
    }

    public ConfigList listConfigs(Empty request) {
        return ConfigList.newBuilder().build();
    }
}
