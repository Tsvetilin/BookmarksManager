package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bookmark extends Entity<String> {
    private final String url;
    private final String shortened;
    private final String title;
    private final List<String> keywords;

    private Group group;
    private final User user;

    public Bookmark(String key, String url, String shortened, String title, Collection<String> keywords, User user) {
        super(key);
        this.url = url;
        this.shortened = shortened;
        this.title = title;
        this.keywords = List.copyOf(keywords);
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public String getShortened() {
        return shortened;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public User getUser() {
        return user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
