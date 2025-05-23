package util;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import data.AppConfig;
import data.CliConfig;
import data.CoreConfig;

public class ConfigLoader {
    private static final Config config = ConfigFactory.load();

    public static CliConfig load() {
        Config appConfig = config.getConfig("cli.app");
        AppConfig app = new AppConfig(
                appConfig.getString("directory")
        );

        Config coreConfig = config.getConfig("cli.core");
        CoreConfig core = new CoreConfig(
                coreConfig.getString("host"),
                coreConfig.getInt("port"),
                coreConfig.getString("binary-path")
        );

        return new CliConfig(app, core);
    }
}
