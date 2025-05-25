package configService.subcommand;

import com.google.inject.Inject;
import core.grpc.ConfigId;
import core.grpc.ConnectionConfigServiceGrpc;
import io.grpc.ManagedChannel;
import picocli.CommandLine;


@CommandLine.Command(name = "delete", description = "Удалить конфиг")
public class ConfigDeleteSubcommand implements Runnable {
    private final ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub;
    private final ManagedChannel channel;

    @Inject
    public ConfigDeleteSubcommand(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub,
                               ManagedChannel channel) {
        this.stub = stub;
        this.channel = channel;
    }

    @CommandLine.Parameters(index = "0", description = "ID конфига")
    private int id;

    @Override
    public void run() {
        var request = ConfigId.newBuilder()
                .setId(id)
                .build();

        stub.deleteConfig(request);
        System.out.println("Конфиг удалён");

        channel.shutdown();
    }
}
