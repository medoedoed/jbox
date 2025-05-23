package configService.subcommand;

import core.grpc.ConfigList;
import core.grpc.ConfigServiceGrpc;
import core.grpc.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "list", description = "config list")
public class ConfigListSubcommand implements Runnable {
    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 48313)
                .usePlaintext()
                .build();

        ConfigServiceGrpc.ConfigServiceBlockingStub stub = ConfigServiceGrpc.newBlockingStub(channel);

        Empty request = Empty.newBuilder().build();
        ConfigList response = stub.listConfigs(request);

        response.getConfigsList().forEach(config ->
                System.out.printf("ID: %d, Name: %s%n", config.getId(), config.getName()));

        channel.shutdown();
    }
}