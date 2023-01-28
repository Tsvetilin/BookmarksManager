package bg.sofia.uni.fmi.mjt.bookmarks.server;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private static final Map<String, Object> CONTAINER;

    static {
        CONTAINER = new HashMap<>();
    }

    public static <T> T request(Class<T> tClass) {
        return (T) CONTAINER.get(tClass.getName());
    }

    public static <T> void register(T object) {
        CONTAINER.put(object.getClass().getName(), object);
    }
}
