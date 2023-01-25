package bg.sofia.uni.fmi.mjt.bookmarks.server.logging;

import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.ConsoleProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.DefaultConsoleProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.DefaultFileProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.FileProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.DateTimeProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.DefaultDateTimeProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

public class BookmarksLogger implements Logger {

    private static final String DEFAULT_LOG_FILE_PATH = "";
    private static final String DEFAULT_ERROR_FILE_PATH = "";

    private final Severity consoleSeverity;
    private final Severity fileSeverity;
    private final String logFilePath;
    private final String errorFilePath;
    private final DateTimeProvider dateTimeProvider;
    private final ConsoleProvider consoleProvider;
    private final FileProvider fileProvider;

    private BookmarksLogger(BookmarksLoggerBuilder builder) {
        this.consoleSeverity = builder.consoleSeverity;
        this.fileSeverity = builder.fileSeverity;
        this.logFilePath = builder.logFilePath;

        this.errorFilePath = Nullable.orDefault(builder.errorFilePath, DEFAULT_ERROR_FILE_PATH);
        this.dateTimeProvider = Nullable.orDefault(builder.dateTimeProvider, new DefaultDateTimeProvider());
        this.consoleProvider = Nullable.orDefault(builder.consoleProvider, new DefaultConsoleProvider());
        this.fileProvider = Nullable.orDefault(builder.fileProvider, new DefaultFileProvider());
    }

    public static BookmarksLoggerBuilder configure() {
        return new BookmarksLoggerBuilder();
    }

    public static BookmarksLogger getDefaultLogger() {
        return new BookmarksLoggerBuilder().addConsoleLogging(Severity.INFO)
            .addFileLogging(Severity.INFO, DEFAULT_LOG_FILE_PATH).build();
    }


    @Override
    public void logInfo(String message) {
        log(Severity.INFO, message);
    }

    @Override
    public void logError(String message) {
        log(Severity.ERROR, message);
    }

    @Override
    public void logException(Exception e) {
        log(Severity.ERROR, "Logged exception!");

        // TODO: do logging
    }

    @Override
    public void log(Severity severity, String message) {
        if (fileSeverity.getValue() >= severity.getValue()) {
            logFile(severity, message);
        }

        if (consoleSeverity.getValue() >= severity.getValue()) {
            logConsole(severity, message);
        }
    }

    private void logFile(Severity severity, String message) {

    }

    private void logConsole(Severity severity, String message) {

    }


    public static class BookmarksLoggerBuilder {

        private Severity consoleSeverity = Severity.NONE;
        private Severity fileSeverity = Severity.NONE;
        private DateTimeProvider dateTimeProvider;
        private ConsoleProvider consoleProvider;
        private FileProvider fileProvider;
        private String logFilePath;
        private String errorFilePath;

        private BookmarksLoggerBuilder() {
        }

        public BookmarksLoggerBuilder addConsoleLogging(Severity severity) {
            consoleSeverity = severity;
            return this;
        }

        public BookmarksLoggerBuilder addFileLogging(Severity severity, String path) {
            logFilePath = path;
            fileSeverity = severity;
            return this;
        }

        public BookmarksLoggerBuilder addConsoleLogging(Severity severity, ConsoleProvider provider) {
            consoleProvider = provider;
            return addConsoleLogging(severity);
        }

        public BookmarksLoggerBuilder addFileLogging(Severity severity, String path, FileProvider provider) {
            fileProvider = provider;
            return addFileLogging(severity, path);
        }

        public BookmarksLoggerBuilder addErrorLogging(String filePath) {
            errorFilePath = filePath;
            return this;
        }

        public BookmarksLoggerBuilder configureDateTimeProvider(DateTimeProvider provider) {
            dateTimeProvider = provider;
            return this;
        }

        public BookmarksLoggerBuilder configureConsoleProvider(ConsoleProvider provider) {
            consoleProvider = provider;
            return this;
        }

        public BookmarksLoggerBuilder configureFileProvider(FileProvider provider) {
            fileProvider = provider;
            return this;
        }

        public BookmarksLogger build() {
            return new BookmarksLogger(this);
        }

    }
}
