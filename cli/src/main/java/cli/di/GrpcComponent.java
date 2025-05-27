package cli.di;

import core.grpc.ConnectionConfigServiceGrpc;
import core.ping.PingServiceGrpc;
import dagger.Component;

@Component(modules = {GrpcModule.class, ConfigModule.class})
public interface GrpcComponent {
    PingServiceGrpc.PingServiceBlockingStub getPingStub();
    ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub getConnectionConfigStub();
}
