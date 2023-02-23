package bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.strategy.StemmingStrategy;

public class DefaultStemmingService implements StemmingService {

    private final StemmingStrategy strategy;

    public DefaultStemmingService(StemmingStrategy strategy) {

        this.strategy = strategy;
    }

    public String stemWord(String word) {
        return strategy.stem(word);
    }

    public String stemText(String text) {
        return "";// TODO: complete
    }
}
