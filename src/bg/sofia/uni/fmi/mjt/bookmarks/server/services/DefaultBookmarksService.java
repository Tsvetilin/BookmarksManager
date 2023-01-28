package bg.sofia.uni.fmi.mjt.bookmarks.server.services;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkValidationException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DefaultBookmarksService implements BookmarksService {

    private final HttpClient client;

    public DefaultBookmarksService(HttpClient client) {
        this.client = client;
    }

    @Override
    public Bookmark generateBookmark(String url, String group, boolean shortened, User user) {
        return null;
    }

    @Override
    public boolean validateUrl(String url) throws BookmarkValidationException {
        try {
            return
                client.send(HttpRequest.newBuilder().uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString())
                    .statusCode() !=
                    HttpURLConnection.HTTP_NOT_FOUND;
        } catch (IOException | InterruptedException e) {
            throw new BookmarkValidationException(e);
        }
    }

}
