package cli.di;

import cli.JBoxApplication;
import cli.util.CoreLoader;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @Provides
    JBoxApplication provideApplication(CoreLoader coreLoader) {
        return new JBoxApplication(coreLoader);
    }
}
