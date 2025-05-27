package cli;

import cli.command.ping.PingCommand;
import cli.di.*;
import cli.util.CoreLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;

import javax.inject.Inject;

@CommandLine.Command(
        name = "jbox",
        version = "jbox 1.0",
        description = "cli client for sing-box core",
        subcommands = {
//                ConfigCommand.class,
                PingCommand.class
        },
        mixinStandardHelpOptions = true
)
public class JBoxApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JBoxApplication.class);

    public CoreLoader coreLoader;

    @Inject
    public JBoxApplication(CoreLoader coreLoader) {
        this.coreLoader = coreLoader;
    }



    @Override
    public void run() {
        try {
            coreLoader.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        logger.info("JBoxApplication started");
    }

    public static void main(String[] args) {
        AppComponent component = DaggerAppComponent.create();
        component.getLogbackConfig().configureLogging();
        CommandComponent serviceComponent = DaggerCommandComponent.create();
        int exitCode = new CommandLine(component.getApp(), new DaggerFactory(serviceComponent)).execute(args);
        System.exit(exitCode);
    }

}


