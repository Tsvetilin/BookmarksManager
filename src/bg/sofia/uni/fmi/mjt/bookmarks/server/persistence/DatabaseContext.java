package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.Repository;

public interface DatabaseContext {

    Repository<String, User> users();

    Repository<String, Bookmark> bookmarks();

    Repository<String, Group> groups();

    void persist();
}
