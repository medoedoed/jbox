package cli.command.config;

import cli.service.GrpcConfigService;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(
        name = "config",
        description = "config service",
        subcommands = {AddJsonConfigCommand.class})
public class ConfigCommand implements Runnable {


    @Override
    public void run() {
        System.out.println(123);
    }
}
