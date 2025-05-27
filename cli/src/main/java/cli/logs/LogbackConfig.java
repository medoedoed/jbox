package cli.logs;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import config.CoreSettings;
import config.data.AppConfig;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.File;

public class LogbackConfig {
    private static final String PATTERN = "%d{HH:mm:ss} %-5level %logger{36} - %msg%n";
    private final CoreSettings settings;
    private final AppConfig appConfig;

    @Inject
    public LogbackConfig(CoreSettings settings, AppConfig appConfig) {
        this.settings = settings;
        this.appConfig = appConfig;
    }


    public void configureLogging() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        var consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setPattern(PATTERN);
        consoleEncoder.start();

        var consoleAppender = new ConsoleAppender<ILoggingEvent>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();

        var fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(context);
        fileEncoder.setPattern(PATTERN);
        fileEncoder.start();

        var fileAppender = new FileAppender<ILoggingEvent>();
        fileAppender.setContext(context);
        fileAppender.setEncoder(fileEncoder);

        String configPath = appConfig.getLogging().getCli().getFile();
        String appPath = settings.getApp().getDirectory();

        fileAppender.setFile(appPath + File.separator + configPath);
        fileAppender.start();

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.detachAndStopAllAppenders();

        String logLevelString = appConfig.getLogging().getCli().getLevel();
        Level level = Level.toLevel(logLevelString.toUpperCase(), Level.INFO);
        rootLogger.setLevel(level);

        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }
}
