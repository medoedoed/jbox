package di;

import com.google.inject.AbstractModule;
import logs.LoggerProvider;
import org.slf4j.Logger;

public class LoggingModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Logger.class).toProvider(LoggerProvider.class);
    }
}
