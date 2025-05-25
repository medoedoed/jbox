package service;

import core.grpc.ConfigList;
import core.grpc.ConnectionConfigServiceGrpc;
import core.grpc.Empty;

public class GrpcConfigService
        extends ConnectionConfigServiceGrpc.ConnectionConfigServiceImplBase {
    public ConfigList listConfigs(Empty request) {
        return ConfigList.newBuilder().build();
    }
}
