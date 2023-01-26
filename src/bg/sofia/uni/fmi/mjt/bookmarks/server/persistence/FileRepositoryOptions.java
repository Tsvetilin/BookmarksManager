package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.DefaultSerializer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.Serializer;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class FileRepositoryOptions {

    private final Reader reader;
    private final Writer writer;
    private final Serializer serializer;

    public static FileRepositoryOptions getDefault() {
        return null;
    }

    private FileRepositoryOptions(FileRepositoryOptionsBuilder builder) throws IOException {
        reader = new FileReader(builder.path);
        writer = new FileWriter(builder.path);
        this.serializer = Nullable.orDefault(builder.serializer, new DefaultSerializer());
    }

    public static FileRepositoryOptionsBuilder create(String path) {
        return new FileRepositoryOptionsBuilder(path);
    }

    public Reader reader() {
        return reader;
    }

    public Writer writer() {
        return writer;
    }

    public Serializer serializer() {
        return serializer;
    }

    private static class FileRepositoryOptionsBuilder {

        private String path;
        private Serializer serializer;

        public FileRepositoryOptionsBuilder(String path) {
            this.path = path;
        }

        public FileRepositoryOptionsBuilder configureSerializer(Serializer serializer) {
            this.serializer = serializer;
            return this;
        }

        public FileRepositoryOptions build() throws IOException {
            return new FileRepositoryOptions(this);
        }

    }
}
