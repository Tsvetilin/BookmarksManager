package bg.sofia.uni.fmi.mjt.bookmarks.server.services;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkValidationException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;

public interface BookmarksService {
    Bookmark generateBookmark(String url, String group, boolean shortened, User user) throws InvalidBookmarkException,
        UrlShortenerException, BookmarkValidationException;

    boolean validateUrl(String url) throws BookmarkValidationException;
}
