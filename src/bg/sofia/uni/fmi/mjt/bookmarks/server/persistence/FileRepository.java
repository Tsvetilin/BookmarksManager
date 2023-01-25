package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.util.Collection;

public class FileRepository<K, T extends Entity<K>> implements Repository<K, T> {

    private final FileRepositoryOptions options;


    public FileRepository(FileRepositoryOptions options) {

        this.options = Nullable.orDefault(options,FileRepositoryOptions.getDefault());
    }

    @Override
    public void add(T object) {

    }

    @Override
    public T get(K key) {
        return null;
    }

    @Override
    public void remove(K key) {

    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public Collection<T> getAll() {
        return null;
    }

    @Override
    public void persist() {

    }
}
