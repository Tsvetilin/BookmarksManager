package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Group extends Entity<String> {
    private final String name;
    private final User user;
    private final List<Bookmark> bookmarks;

    public Group(String key, String name, User user) {
        super(key);
        this.name = name;
        this.user = user;
        this.bookmarks = new ArrayList<>();

        Nullable.throwIfAnyNull(name, user);
    }


    public String getName() {
        return name;
    }

    public User getUser() {
        return user;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public List<Bookmark> bookmarksSynchronizer() {
        return bookmarks;
    }
}
