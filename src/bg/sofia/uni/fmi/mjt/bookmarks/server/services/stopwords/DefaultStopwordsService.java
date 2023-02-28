package bg.sofia.uni.fmi.mjt.bookmarks.server.services.stopwords;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.StopWordsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultStopwordsService implements StopwordsService {
    private static final String DEFAULT_FILE_PATH = "resources/stopwords.txt";
    private final Set<String> stopwords;
    private boolean isLoaded;

    public DefaultStopwordsService() {
        stopwords = new HashSet<>();
        isLoaded = false;
    }

    @Override
    public void ensureLoaded() throws StopWordsException {
        if (!isLoaded) {
            throw new StopWordsException("Stopwords not loaded.");
        }
    }

    @Override
    public Collection<String> list() {
        return stopwords;
    }

    @Override
    public boolean isStopword(String word) {
        return stopwords.contains(word.trim().toLowerCase());
    }


    @Override
    public void add(String word) {
        stopwords.add(word);
        isLoaded = true;
    }

    @Override
    public void load() throws StopWordsException {
        load(DEFAULT_FILE_PATH);
    }

    @Override
    public void load(String path) throws StopWordsException {
        Nullable.throwIfNull(path);

        try (var reader = new FileReader(path)) {
            load(reader);
        } catch (IOException e) {
            throw new StopWordsException(e);
        }
    }

    @Override
    public void load(Reader reader) throws StopWordsException {
        Nullable.throwIfNull(reader);

        stopwords.clear();
        isLoaded = false;

        try (var bufferedReader = new BufferedReader(reader)) {
            stopwords.addAll(bufferedReader.lines().collect(Collectors.toSet()));
            isLoaded = true;
        } catch (IOException e) {
            throw new StopWordsException(e);
        }
    }
}
