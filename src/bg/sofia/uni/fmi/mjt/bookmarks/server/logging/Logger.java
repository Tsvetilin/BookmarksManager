package bg.sofia.uni.fmi.mjt.bookmarks.server.logging;

public interface Logger {
    void logInfo(String str);
    void logError(String str);
    void logException(Exception e);
}
