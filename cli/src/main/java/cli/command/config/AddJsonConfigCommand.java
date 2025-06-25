package cli.command.config;

import cli.service.GrpcConfigService;
import picocli.CommandLine;

import javax.inject.Inject;
import java.util.Scanner;

@CommandLine.Command(
        name = "json",
        description = "add config from json")
public class AddJsonConfigCommand implements Runnable {
    private final GrpcConfigService configService;

    @Inject
    public AddJsonConfigCommand(GrpcConfigService configService) {
        this.configService = configService;
    }

    @Override
    public void run() {
        System.out.print("json path: ");
        Scanner scanner = new Scanner(System.in);
        String path = scanner.nextLine();

        System.out.print("connection name: ");
        String connectionName = scanner.nextLine();
        scanner.close();

        configService.putConfigJson(connectionName, path);
    }
}
