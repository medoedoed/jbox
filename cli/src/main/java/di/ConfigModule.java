package di;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import config.AppConfigServiceKt;
import config.CoreSettings;
import config.CoreSettingsKt;
import config.data.AppConfig;

import java.nio.file.Path;

public class ConfigModule extends AbstractModule {
    @Provides
    @Singleton
    CoreSettings provideCoreSettings() {
        return CoreSettingsKt.loadSettings();
    }

    @Provides
    @Singleton
    AppConfig provideAppConfig(CoreSettings coreSettings) {
        return AppConfigServiceKt.loadAppConfig(Path.of(coreSettings.getApp().getAppConfig()));
    }
}
