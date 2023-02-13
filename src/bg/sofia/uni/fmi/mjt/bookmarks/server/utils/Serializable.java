package bg.sofia.uni.fmi.mjt.bookmarks.server.utils;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.Serializer;

import java.io.Writer;

public interface Serializable {
    void serialize(Writer writer, Serializer serializer);
}
