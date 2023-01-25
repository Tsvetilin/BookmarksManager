package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import java.util.Collection;

public interface Repository<K, T extends Entity<K>> {

    void add(T object);

    T get(K key);

    void remove(K key);

    boolean contains(K key);

    Collection<T> getAll();

    void persist();

}
