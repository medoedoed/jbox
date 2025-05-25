package di;

import cli.JBoxApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ConfigModule());
        install(new GrpcModule());
        install(new LoggingModule());
    }

    @Provides
    JBoxApplication provideApplicationLoader() {
        return new JBoxApplication();
    }

}
