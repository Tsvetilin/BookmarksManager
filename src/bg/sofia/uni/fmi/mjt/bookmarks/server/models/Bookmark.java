package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.SerializableEntity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.Serializer;

import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bookmark extends SerializableEntity<String> {
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

        Nullable.throwIfAnyNull(url, user);
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

    public String listPreview() {
        return title + " - " + url;
    }

    @Override
    public void serialize(Writer writer, Serializer serializer) {

    }
}
