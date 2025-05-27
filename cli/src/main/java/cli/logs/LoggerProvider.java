package cli.logs;

import dagger.internal.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider implements Provider<Logger> {
    private final Class<?> clazz;

    // Конструктор без @Inject и без параметров
    public LoggerProvider(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Logger get() {
        return LoggerFactory.getLogger(clazz);
    }
}
