package bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.providers;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.LoggerOperationException;

public interface Provider {
    void write(String str) throws LoggerOperationException;
}
