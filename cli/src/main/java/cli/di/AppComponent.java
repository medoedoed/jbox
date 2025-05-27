package cli.di;

import cli.JBoxApplication;
import cli.logs.LogbackConfig;
import dagger.Component;

@Component(modules = { CoreModule.class, ConfigModule.class})
public interface AppComponent {

    JBoxApplication getApp();
    LogbackConfig getLogbackConfig();

}


