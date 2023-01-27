package bg.sofia.uni.fmi.mjt.bookmarks.server;

import java.util.HashMap;
import java.util.Map;

public class DIContainer {
    private static final Map<String, Object> container;

    static {
        container = new HashMap<>();
    }

    public static <T> T request(Class<T> tClass) {
        return (T) container.get(tClass.getName());
    }

    public static <T> void register(T object) {
        container.put(object.getClass().getName(), object);
    }
}
