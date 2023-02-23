package bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.strategy;

import java.util.UUID;

public class UUIDGeneratorStrategy implements GeneratorStrategy {
    @Override
    public String generateNext() {
        return UUID.randomUUID().toString();
    }
}
