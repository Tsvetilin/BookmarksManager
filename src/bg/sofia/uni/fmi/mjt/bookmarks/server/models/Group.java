package bg.sofia.uni.fmi.mjt.bookmarks.server.models;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        return Collections.unmodifiableList(bookmarks);
    }

    public List<Bookmark> bookmarksSynchronizer() {
        return bookmarks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return name.equals(group.name) && user.equals(group.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, user);
    }
}
