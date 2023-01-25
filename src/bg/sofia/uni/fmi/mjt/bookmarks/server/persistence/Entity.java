package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

public class Entity<K> {
    private final K key;

    public Entity(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }
}
