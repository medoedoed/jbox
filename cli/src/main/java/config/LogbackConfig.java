package config;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import com.google.inject.Guice;
import com.google.inject.Injector;
import config.data.AppConfig;
import di.ConfigModule;
import org.slf4j.LoggerFactory;

import java.io.File;

public class LogbackConfig {
    private static final String pattern = "%d{HH:mm:ss} %-5level %logger{36} - %msg%n";

    public static void configureLogging() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setPattern(pattern);
        consoleEncoder.start();

        ConsoleAppender<ILoggingEvent> consoleAppender = new ConsoleAppender<>();
        consoleAppender.setContext(context);
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();

        PatternLayoutEncoder fileEncoder = new PatternLayoutEncoder();
        fileEncoder.setContext(context);
        fileEncoder.setPattern(pattern);
        fileEncoder.start();

        FileAppender<ILoggingEvent> fileAppender = new FileAppender<>();
        fileAppender.setContext(context);
        fileAppender.setEncoder(fileEncoder);

        Injector injector = Guice.createInjector(new ConfigModule());
        var appConfig = injector.getProvider(AppConfig.class).get();
        var settings = injector.getProvider(CoreSettings.class).get();

        var configPath = appConfig.getLogging().getCli().getFile();
        var appPath = settings.getApp().getDirectory();

        fileAppender.setFile(appPath + File.separator + configPath);
        fileAppender.start();

        Logger rootLogger = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(Level.DEBUG);

        rootLogger.addAppender(consoleAppender);
        rootLogger.addAppender(fileAppender);
    }
}
