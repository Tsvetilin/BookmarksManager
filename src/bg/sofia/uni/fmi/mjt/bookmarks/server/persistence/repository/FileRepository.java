package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.observe.Observable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileRepository<K, T extends Entity<K>> extends Observable<T> implements Repository<K, T> {

    private final FileRepositoryOptions options;
    private final Map<K, T> table;

    public FileRepository(FileRepositoryOptions options) {
        this.options = Nullable.orDefault(options, FileRepositoryOptions.getDefault());
        this.table = new HashMap<>();
        loadData();
    }

    private void loadData() {
        table.clear();
        var json = new BufferedReader(options.reader()).lines().collect(Collectors.joining(System.lineSeparator()));
        table.putAll(options.serializer().deserialize(json, table.getClass()));
    }

    @Override
    public void add(T object) {
        table.put(object.getKey(), object);
        notifyAdd(object);
    }

    @Override
    public Optional<T> get(K key) {
        return Optional.ofNullable(table.get(key));
    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        return table.values().stream().filter(predicate).findFirst();
    }

    @Override
    public boolean any(Predicate<T> predicate) {
        return table.values().stream().anyMatch(predicate);
    }

    @Override
    public void remove(K key) {
        notifyRemove(table.get(key));
        table.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return table.containsKey(key);
    }

    @Override
    public Collection<T> getAll() {
        return table.values();
    }

    @Override
    public void persist() {
        try {
            options.writer().write(options.serializer().serialize(table));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
