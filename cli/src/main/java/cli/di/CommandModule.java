package cli.di;

import cli.command.PingCommand;
import cli.command.config.AddJsonConfigCommand;
import cli.command.config.ConfigCommand;
import cli.service.GrpcConfigService;
import cli.service.GrpcPingService;
import dagger.Module;
import dagger.Provides;

@Module
public class CommandModule {
    @Provides
    PingCommand providePingCommand(GrpcPingService pingService) {
        return new PingCommand(pingService);
    }

    @Provides
    ConfigCommand provideConfigCommand() {
        return new ConfigCommand();
    }

    @Provides
    AddJsonConfigCommand provideAddJsonConfigCommand(GrpcConfigService configService) {
        return new AddJsonConfigCommand(configService);
    }
}

