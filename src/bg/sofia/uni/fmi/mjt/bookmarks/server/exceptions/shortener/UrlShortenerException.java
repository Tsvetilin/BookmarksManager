package bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.shortener;

public class UrlShortenerException extends Exception {

    public UrlShortenerException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlShortenerException(String message) {
        super(message);
    }
}
