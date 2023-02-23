package bg.sofia.uni.fmi.mjt.bookmarks.server.services.stopwords;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.StopWordsException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

import java.io.Reader;
import java.util.Collection;

public interface StopwordsService extends Service {

    void ensureLoaded() throws StopWordsException;

    Collection<String> list();

    boolean isStopword(String word);

    void add(String word);

    void load() throws StopWordsException;

    void load(String path) throws StopWordsException;

    void load(Reader reader) throws StopWordsException;
}
