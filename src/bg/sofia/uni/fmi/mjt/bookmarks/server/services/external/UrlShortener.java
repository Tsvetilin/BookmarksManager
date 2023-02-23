package bg.sofia.uni.fmi.mjt.bookmarks.server.services.external;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

public interface UrlShortener extends Service {

    String shorten(String url) throws UrlShortenerException;
}
