package di;

import cli.JBoxApplication;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class ApplicationModule extends AbstractModule {
    @Provides
    JBoxApplication provideApplicationLoader() {
        return new JBoxApplication();
    }

}
