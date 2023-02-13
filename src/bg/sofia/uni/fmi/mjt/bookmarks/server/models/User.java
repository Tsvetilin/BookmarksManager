package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.SerializableEntity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.Serializer;

import java.awt.print.Book;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User extends SerializableEntity<String> {
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

        Nullable.throwIfAnyNull(username, password);
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    // Unmodifiable view
    public List<Bookmark> getBookmarks() {
        return Collections.unmodifiableList(bookmarks);
    }

    // Unmodifiable view
    public List<Group> getGroups() {
        return Collections.unmodifiableList(groups);
    }

    public List<Bookmark> bookmarksSynchronizer() {
        return bookmarks;
    }

    public List<Group> groupsSynchronizer() {
        return groups;
    }

    @Override
    public void serialize(Writer writer, Serializer serializer) {

    }
}
