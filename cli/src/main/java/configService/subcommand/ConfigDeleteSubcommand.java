package configService.subcommand;

import core.grpc.ConfigId;
import core.grpc.ConfigServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "delete", description = "Удалить конфиг")
public class ConfigDeleteSubcommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "ID конфига")
    private int id;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 48313)
                .usePlaintext()
                .build();

        var stub = ConfigServiceGrpc.newBlockingStub(channel);

        var request = ConfigId.newBuilder()
                .setId(id)
                .build();

        stub.deleteConfig(request);
        System.out.println("Конфиг удалён");

        channel.shutdown();
    }
}
