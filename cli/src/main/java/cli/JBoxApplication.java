package cli;

import com.google.inject.Guice;
import com.google.inject.Injector;
import configService.ConfigCommand;
import di.AppModule;
import di.ConfigModule;
import io.grpc.ManagedChannel;
import logs.LogbackConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine;
import util.CoreLoader;

@CommandLine.Command(
        name = "jbox",
        version = "jbox 1.0",
        description = "cli client for sing-box core",
        subcommands = {
                ConfigCommand.class
        }
)
public class JBoxApplication implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(JBoxApplication.class);

    @Override
    public void run() {
        try {
            Injector injector = Guice.createInjector(new ConfigModule());
            CoreLoader coreLoader = injector.getInstance(CoreLoader.class);
            coreLoader.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        logger.info("JBoxApplication started");
    }

    public static void main(String[] args) {
        LogbackConfig.configureLogging();
        Injector injector = Guice.createInjector(new AppModule());

        // Проверка соединения с gRPC сервером
        var channel = injector.getInstance(ManagedChannel.class);


        var app = injector.getInstance(JBoxApplication.class);
        int exitCode = new CommandLine(app).execute(args);

        channel.shutdown();
        System.exit(exitCode);
    }

}


