package bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

public interface Logger extends Service {
    void logInfo(String message);

    void logWarning(String message);

    // TODO: add overloaded with exception
    void logError(String message);

    void logException(Exception e, String id);

    void log(Severity severity, String message);
}
