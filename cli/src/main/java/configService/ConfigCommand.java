package configService;

import configService.subcommand.ConfigAddSubcommand;
import configService.subcommand.ConfigDeleteSubcommand;
import configService.subcommand.ConfigListSubcommand;
import picocli.CommandLine;

@CommandLine.Command(
        name = "config",
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
