package bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.strategy.GeneratorStrategy;

import java.util.HashSet;
import java.util.Set;

public class DefaultIdGeneratorService implements IdGeneratorService {

    private final GeneratorStrategy strategy;
    private final Set<String> IDS;

    public DefaultIdGeneratorService(GeneratorStrategy strategy) {
        IDS = new HashSet<>();
        this.strategy = strategy;
    }

    @Override
    public String generateId() {
        String id;
        do {
            id = strategy.generateNext();
        } while (IDS.contains(id));

        return id;
    }

    @Override
    public void addUsedId(String id) {
        IDS.add(id);
    }

    @Override
    public void clear() {
        IDS.clear();
    }

}
