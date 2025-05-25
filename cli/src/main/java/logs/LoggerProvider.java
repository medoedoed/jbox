package logs;

import com.google.inject.Inject;
import com.google.inject.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProvider implements Provider<Logger> {
    private final Class<?> clazz;

    @Inject
    public LoggerProvider(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Logger get() {
        return LoggerFactory.getLogger(clazz);
    }
}
