package cli.di;

import cli.service.GrpcConfigService;
import cli.service.GrpcPingService;
import core.grpc.ConnectionConfigServiceGrpc;
import core.ping.PingServiceGrpc;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Provides
    GrpcPingService providePingService(PingServiceGrpc.PingServiceBlockingStub stub) {
        return new GrpcPingService(stub);
    }

    @Provides
    GrpcConfigService provideConfigService(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub) {
        return new GrpcConfigService(stub);
    }
}
