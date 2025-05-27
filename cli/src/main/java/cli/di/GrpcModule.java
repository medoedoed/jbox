package cli.di;

import config.data.AppConfig;
import core.grpc.ConnectionConfigServiceGrpc;
import core.ping.PingServiceGrpc;
import dagger.Module;
import dagger.Provides;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Module
public class GrpcModule {
    @Provides
    ManagedChannel provideManagedChannel(AppConfig appConfig) {
        return ManagedChannelBuilder
                .forAddress(appConfig.getGrpc().getHost(), appConfig.getGrpc().getPort())
                .usePlaintext()
                .build();
    }

    @Provides
    ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub provideBlockingStub(ManagedChannel channel) {
        return ConnectionConfigServiceGrpc.newBlockingStub(channel);
    }

    @Provides
    PingServiceGrpc.PingServiceBlockingStub providePingServiceBlockingStub(ManagedChannel channel) {
        return PingServiceGrpc.newBlockingStub(channel);
    }
}
