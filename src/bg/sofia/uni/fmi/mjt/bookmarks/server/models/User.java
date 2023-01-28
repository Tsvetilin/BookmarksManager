package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;

import java.util.ArrayList;
import java.util.List;

public class User extends Entity<String> {
    private final String username;
    private final String password;

    private final List<Bookmark> bookmarks;
    private final List<Group> groups;

    public User(String key, String username, String password) {
        super(key);
        this.username = username;
        this.password = password;
        this.bookmarks = new ArrayList<>();
        this.groups = new ArrayList<>();
    }


    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public List<Bookmark> getBookmarks() {
        return bookmarks;
    }

    public List<Group> getGroups() {
        return groups;
    }
}
