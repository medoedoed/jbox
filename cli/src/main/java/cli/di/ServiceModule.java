package cli.di;

import cli.service.GrpcPingService;
import core.ping.PingServiceGrpc;
import dagger.Module;
import dagger.Provides;

@Module
public class ServiceModule {
    @Provides
    GrpcPingService providePingService(PingServiceGrpc.PingServiceBlockingStub stub) {
        return new GrpcPingService(stub);
    }
}
