package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;

import java.util.List;

public class Group extends Entity<String> {
    private String name;

    private User user;
    private List<Bookmark> bookmarks;

    public Group(String key) {
        super(key);
    }
}
