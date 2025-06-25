package cli.di;

import cli.JBoxApplication;
import cli.command.PingCommand;
import cli.logs.LogbackConfig;
import cli.service.GrpcPingService;
import dagger.Component;

@Component(modules = {
        ConfigModule.class,
        GrpcModule.class,
        ServiceModule.class,
        CommandModule.class,
        AppModule.class
})
public interface AppComponent {
    JBoxApplication getApp();
    PingCommand getPingCommand();
    GrpcPingService getPingService();
    LogbackConfig getLogbackConfig();
}



