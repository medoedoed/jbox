package configService.subcommand;

import com.google.inject.Inject;
import core.grpc.ConfigList;
import core.grpc.ConnectionConfigServiceGrpc;
import core.grpc.Empty;
import io.grpc.ManagedChannel;
import picocli.CommandLine;


@CommandLine.Command(name = "list", description = "config list")
public class ConfigListSubcommand implements Runnable {
    private final ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub;
    private final ManagedChannel channel;
    @Inject
    public ConfigListSubcommand(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub,
                                  ManagedChannel channel) {
        this.stub = stub;
        this.channel = channel;
    }


    @Override
    public void run() {


        Empty request = Empty.newBuilder().build();
        ConfigList response = stub.listConfigs(request);

        response.getConfigsList().forEach(config ->
                System.out.printf("ID: %d, Name: %s%n", config.getId(), config.getName()));

        channel.shutdown();
    }
}