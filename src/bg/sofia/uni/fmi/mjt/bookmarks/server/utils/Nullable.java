package bg.sofia.uni.fmi.mjt.bookmarks.server.utils;

public abstract class Nullable {
    public static <T> T orDefault(T object, T defaultValue) {
        if (object == null) {
            return defaultValue;
        }

        return object;
    }
}
