package cli.di;

import cli.util.CoreLoader;
import config.CoreSettings;
import dagger.Module;
import dagger.Provides;


@Module
public class CoreModule {
    @Provides
    CoreLoader providesCoreLoader(CoreSettings settings) {
        return new CoreLoader(settings);
    }
}
