package bg.sofia.uni.fmi.mjt.bookmarks.server;

import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private static final Map<String, Object> CONTAINER;

    static {
        CONTAINER = new HashMap<>();
    }

    public static <T> T request(Class<T> tClass) {
        var result = CONTAINER.get(tClass.getName());
        if (result == null) {
            throw new IllegalArgumentException("Not such service registered");
        }

        return (T) result;
    }

    public static <T> void register(T object) {
        CONTAINER.put(object.getClass().getName(), object);
    }
}
