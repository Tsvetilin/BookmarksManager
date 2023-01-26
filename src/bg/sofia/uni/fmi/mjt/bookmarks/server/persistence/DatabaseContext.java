package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;

public interface DatabaseContext {

    Repository<String, User> users();

    Repository<String, Bookmark> bookmarks();

    Repository<String, Group> groups();

    void persist();
}
