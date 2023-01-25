package bg.sofia.uni.fmi.mjt.bookmarks.server.logging;

public interface Logger {
    void logInfo(String message);

    void logError(String message);

    void logException(Exception e);

    void log(Severity severity, String message);
}
