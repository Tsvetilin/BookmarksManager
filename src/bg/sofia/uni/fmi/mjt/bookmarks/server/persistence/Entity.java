package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import java.util.UUID;

public class Entity<K> {
    private final K key;

    public Entity(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }
}
