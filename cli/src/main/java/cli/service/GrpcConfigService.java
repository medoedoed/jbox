package cli.service;

import core.grpc.*;

import javax.inject.Inject;

public class GrpcConfigService
        extends ConnectionConfigServiceGrpc.ConnectionConfigServiceImplBase {
    private final ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub;

    @Inject
    public GrpcConfigService(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub) {
        this.stub = stub;
    }

    public ConfigList listConfigs() {
        return stub.listConfigs(Empty.getDefaultInstance());
    }

    public ConfigResponse putConfigJson(String name, String jsonPath) {
        return stub.addConfigFromJson(ConfigJsonInput.newBuilder().setName(name).setJsonPath(jsonPath).build());
    }
}
