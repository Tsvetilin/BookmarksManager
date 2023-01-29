package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

import java.io.IOException;

public class LoggerOperationException extends Exception {
    public LoggerOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public LoggerOperationException(Exception e) {
        super(e);
    }
}
