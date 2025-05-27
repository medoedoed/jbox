package cli.di;

import cli.command.ping.PingCommand;
import picocli.CommandLine;

public class DaggerFactory implements CommandLine.IFactory {
    private final CommandComponent component;

    public DaggerFactory(CommandComponent component) {
        this.component = component;
    }

    @Override
    public <K> K create(Class<K> cls) throws Exception {
        if (cls.equals(PingCommand.class)) {
            return cls.cast(component.getPingCommand());
        }

        return cls.getDeclaredConstructor().newInstance();
    }
}
