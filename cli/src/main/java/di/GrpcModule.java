//package di;
//
//import config.data.AppConfig;
//import core.grpc.ConnectionConfigServiceGrpc;
//import dagger.Module;
//import dagger.Provides;
//import io.grpc.ManagedChannel;
//import io.grpc.ManagedChannelBuilder;
//
//import javax.inject.Singleton;
//
//@Module
//public class GrpcModule {
//    @Provides
//    @Singleton
//    static ManagedChannel provideChannel(AppConfig config) {
//        return ManagedChannelBuilder.forAddress(config.getGrpc().getHost(), config.getGrpc().getPort())
//                .usePlaintext()
//                .build();
//    }
//
//    @Provides
//    static ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub provideStub(ManagedChannel channel) {
//        return ConnectionConfigServiceGrpc.newBlockingStub(channel);
//    }
//}
