package di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import config.CoreSettings;
import util.CoreLoader;


public class CoreModule extends AbstractModule {
    private final CoreSettings settings;

    public CoreModule(CoreSettings settings) {
        this.settings = settings;
    }

    @Override
    protected void configure() {
        bind(CoreSettings.class).toInstance(settings);
    }

    @Provides
    CoreLoader provideCoreLoader() {
        return new CoreLoader(settings);
    }
}
