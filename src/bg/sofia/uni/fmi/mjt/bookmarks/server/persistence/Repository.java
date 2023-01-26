package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import java.util.Collection;
import java.util.Optional;

public interface Repository<K, T extends Entity<K>> {

    void add(T object);

    Optional<T> get(K key);

    void remove(K key);

    boolean contains(K key);

    Collection<T> getAll();

    void persist();

}
