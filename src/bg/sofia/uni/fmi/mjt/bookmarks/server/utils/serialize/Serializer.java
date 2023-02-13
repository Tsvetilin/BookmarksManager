package bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize;

import java.lang.reflect.Type;

public interface Serializer {
    <T> String serialize(T object);

    <T> String serialize(T object, Type type);

    <T> T deserialize(String object, Type type);
}
