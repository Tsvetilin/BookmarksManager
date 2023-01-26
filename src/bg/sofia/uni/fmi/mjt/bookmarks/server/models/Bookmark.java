package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;

import java.util.List;

public class Bookmark extends Entity<String> {
    private String url;
    private String shortened;
    private String title;
    private List<String> keywords;

    private Group group;
    private User user;

    public Bookmark(String key) {
        super(key);
    }
}
