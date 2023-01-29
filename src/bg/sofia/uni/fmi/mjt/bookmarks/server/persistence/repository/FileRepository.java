package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository;

import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.Entity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FileRepository<K, T extends Entity<K>> implements Repository<K, T> {

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
        table.putAll(options.serializer().deserialize(json));
    }

    @Override
    public void add(T object) {

    }

    @Override
    public Optional<T> get(K key) {
        return Optional.empty();
    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        return Optional.empty();
    }

    @Override
    public boolean any(Predicate<T> predicate) {
        return false;
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
        return table.values();
    }

    @Override
    public void persist() {

    }
}
