package bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class DefaultSerializer implements Serializer {

    private final Gson gson = new Gson();

    @Override
    public <T> String serialize(T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> T deserialize(String object, Type type) {
        return gson.fromJson(object, type);
    }
}
