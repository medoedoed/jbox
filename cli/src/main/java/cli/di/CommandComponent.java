package cli.di;

import cli.command.ping.PingCommand;
import dagger.Component;

@Component(modules = {GrpcModule.class, ServiceModule.class, ConfigModule.class})
public interface CommandComponent {
    PingCommand getPingCommand();
}
