package di;

import com.google.inject.AbstractModule;
import config.LogbackConfig;
import config.LoggerProvider;
import org.slf4j.Logger;

public class LoggingModule extends AbstractModule {
    @Override
    protected void configure() {
        LogbackConfig.configureLogging();
        bind(Logger.class).toProvider(LoggerProvider.class);
    }
}
