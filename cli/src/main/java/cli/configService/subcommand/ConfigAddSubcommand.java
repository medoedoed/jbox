package cli.configService.subcommand;

import core.grpc.ConfigUriInput;
import core.grpc.ConnectionConfigServiceGrpc;
import io.grpc.ManagedChannel;
import picocli.CommandLine;

import javax.inject.Inject;


@CommandLine.Command(name = "add", description = "Добавить конфиг")
public class ConfigAddSubcommand implements Runnable {
    private final ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub;
    private final ManagedChannel channel;

    @CommandLine.Parameters(index = "0", description = "Имя")
    private String name;

    @CommandLine.Parameters(index = "1", description = "JSON")
    private String jsonPath;

    @Inject
    public ConfigAddSubcommand(ConnectionConfigServiceGrpc.ConnectionConfigServiceBlockingStub stub,
                               ManagedChannel channel) {
        this.stub = stub;
        this.channel = channel;
    }

    @Override
    public void run() {
        var request = ConfigUriInput.newBuilder()
                .setName(name)
                .setUri(jsonPath)
                .build();

        var response = stub.addConfigFromUri(request);
        System.out.println("Config added: " + response.getId());

        channel.shutdown();
    }
}
