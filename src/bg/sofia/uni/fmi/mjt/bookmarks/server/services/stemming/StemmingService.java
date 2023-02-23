package bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

public interface StemmingService extends Service {
    String stemWord(String word);

    String stemText(String text);
}
