package cli.di;


import cli.logs.LogbackConfig;
import config.AppConfigServiceKt;
import config.CoreSettings;
import config.CoreSettingsKt;
import config.data.AppConfig;
import dagger.Module;
import dagger.Provides;

import java.nio.file.Path;

@Module
public class ConfigModule {
    @Provides
    CoreSettings provideCoreSettings() {
        return CoreSettingsKt.loadSettings();
    }

    @Provides
    AppConfig provideAppConfig(CoreSettings coreSettings) {
        return AppConfigServiceKt.loadAppConfig(Path.of(coreSettings.getApp().getAppConfig()));
    }

    @Provides
    LogbackConfig provideLogbackConfig(CoreSettings settings, AppConfig appConfig) {
        return new LogbackConfig(settings, appConfig);
    }
}
