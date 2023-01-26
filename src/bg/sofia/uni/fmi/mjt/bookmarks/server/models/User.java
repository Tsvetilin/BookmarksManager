package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;

import java.util.List;

public class User extends Entity<String> {
    private String username;
    private String password;

    private List<Bookmark> bookmarks;
    private List<Group> groups;

    public User(String key) {
        super(key);
    }
}
