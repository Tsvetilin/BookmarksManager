package bg.sofia.uni.fmi.mjt.bookmarks.server.external;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;

public interface UrlShortener {

    String shorten(String url) throws UrlShortenerException;
}
