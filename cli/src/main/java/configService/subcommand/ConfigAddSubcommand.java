package configService.subcommand;

import core.grpc.ConfigInput;
import core.grpc.ConfigServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import picocli.CommandLine;

@CommandLine.Command(name = "add", description = "Добавить конфиг")
public class ConfigAddSubcommand implements Runnable {
    @CommandLine.Parameters(index = "0", description = "Имя")
    private String name;

    @CommandLine.Parameters(index = "1", description = "JSON")
    private String jsonPath;

    @Override
    public void run() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 48313)
                .usePlaintext()
                .build();

        var stub = ConfigServiceGrpc.newBlockingStub(channel);

        var request = ConfigInput.newBuilder()
                .setName(name)
                .setJsonPath(jsonPath)
                .build();

        var response = stub.addConfig(request);
        System.out.println("Config added: " + response.getId());

        channel.shutdown();
    }
}
