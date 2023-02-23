package bg.sofia.uni.fmi.mjt.bookmarks.server.services.serialize;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.serialize.models.BookmarkSerializer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.serialize.models.GroupSerializer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.serialize.models.UserSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class DefaultSerializer implements Serializer {
    private final Gson gson;

    public DefaultSerializer() {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(User.class, new UserSerializer());
        gsonBuilder.registerTypeAdapter(Group.class, new GroupSerializer());
        gsonBuilder.registerTypeAdapter(Bookmark.class, new BookmarkSerializer());

        gson = gsonBuilder.create();
    }

    @Override
    public <T> String serialize(T object) {
        return gson.toJson(object);
    }

    @Override
    public <T> String serialize(T object, Type type) {
        return gson.toJson(object, type);
    }

    @Override
    public <T> T deserialize(String object, Type type) {
        return gson.fromJson(object, type);
    }
}
