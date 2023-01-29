package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions;

import java.io.IOException;

public class InvalidBookmarkException extends Exception {
    public InvalidBookmarkException(Exception e) {
        super(e);
    }
}
