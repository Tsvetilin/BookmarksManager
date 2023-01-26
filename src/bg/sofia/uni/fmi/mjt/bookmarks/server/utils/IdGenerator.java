package bg.sofia.uni.fmi.mjt.bookmarks.server.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class IdGenerator {
    private static final Set<String> ids;

    static {
        ids = new HashSet<>();
    }

    public static String generateId() {
        String id;
        do {
            id = UUID.randomUUID().toString();
        } while (ids.contains(id));

        return id;
    }

    public static void addUsedId(String id) {
        ids.add(id);
    }
}
