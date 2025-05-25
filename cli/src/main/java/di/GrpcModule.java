package di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import config.data.AppConfig;
import core.grpc.ConnectionConfigServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


public class GrpcModule extends AbstractModule {
    @Provides
    @Singleton
    ManagedChannel provideManagedChannel(AppConfig appConfig) {
        return ManagedChannelBuilder
                .forAddress(appConfig.getGrpc().getHost(), appConfig.getGrpc().getPort())
                .usePlaintext()
                .build();
    }

    @Provides
    @Singleton
    ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub provideBlockingStub(ManagedChannel channel) {
        return ConnectionConfigServiceGrpc.newBlockingStub(channel);
    }
}
