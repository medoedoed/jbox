package cli.configService;

import cli.configService.subcommand.ConfigAddSubcommand;
import cli.configService.subcommand.ConfigDeleteSubcommand;
import cli.configService.subcommand.ConfigListSubcommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "logs",
        description = "vless config managment",
        subcommands = {
                ConfigListSubcommand.class,
                ConfigAddSubcommand.class,
                ConfigDeleteSubcommand.class
        }
)
public class ConfigCommand implements Runnable {
    public ConfigCommand() {}
    @Override
    public void run() {
        System.out.println("available commands: list, add, delete");
    }
}
