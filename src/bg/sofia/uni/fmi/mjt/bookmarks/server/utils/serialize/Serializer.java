package bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize;

public interface Serializer {
    <T> String serialize(T object);
    <T> T deserialize(String object);
}
