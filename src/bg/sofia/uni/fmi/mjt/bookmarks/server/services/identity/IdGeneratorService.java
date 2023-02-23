package bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

public interface IdGeneratorService extends Service {
    String generateId();

    void addUsedId(String id);

    void clear();
}
