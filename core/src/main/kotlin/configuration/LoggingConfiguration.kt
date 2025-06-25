package core.configuration

import ch.qos.logback.classic.Level
import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.FileAppender
import config.CoreSettings
import org.koin.core.context.GlobalContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File



fun configureLogging(config: config.data.LoggingConfig) {

    val context = LoggerFactory.getILoggerFactory() as LoggerContext
    context.reset()

    val pattern = "%d{HH:mm:ss} %-5level %logger{36} - %msg%n"

    val encoder = PatternLayoutEncoder().apply {
        this.context = context
        this.pattern = pattern
        start()
    }

    val consoleAppender = ConsoleAppender<ILoggingEvent>().apply {
        this.context = context
        this.encoder = encoder
        start()
    }

    val coreSettings: CoreSettings = GlobalContext.get().get()

    val logFile = File(coreSettings.app.directory, config.core.file)
    logFile.parentFile?.mkdirs()

    val fileAppender = FileAppender<ILoggingEvent>().apply {
        this.context = context
        this.encoder = encoder
        this.file = logFile.absolutePath
        start()
    }

    val level = Level.toLevel(config.core.level.uppercase(), Level.INFO)

    val rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME).apply {
        this.level = level
        addAppender(consoleAppender)
        addAppender(fileAppender)
    }
}