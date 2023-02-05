package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.LoggerOperationException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.DefaultSerializer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.serialize.Serializer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRepositoryOptions {
    private final String path;
    private final Serializer serializer;

    public static FileRepositoryOptions getDefault() {
        return null;
    }

    private FileRepositoryOptions(FileRepositoryOptionsBuilder builder) {
        this.path = builder.path;
        this.serializer = Nullable.orDefault(builder.serializer, new DefaultSerializer());
    }

    public static FileRepositoryOptionsBuilder create(String path) {
        return new FileRepositoryOptionsBuilder(path);
    }

    public Reader reader() {
        try {
            ensureCreated(path);
            return new FileReader(path);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Writer writer() {
        try {
            ensureCreated(path);
            return new FileWriter(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Serializer serializer() {
        return serializer;
    }

    private void ensureCreated(String path) {
        Path dir = Path.of(path);
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static class FileRepositoryOptionsBuilder {

        private final String path;
        private Serializer serializer;

        public FileRepositoryOptionsBuilder(String path) {
            this.path = path;
        }

        public FileRepositoryOptionsBuilder configureSerializer(Serializer serializer) {
            this.serializer = serializer;
            return this;
        }

        public FileRepositoryOptions build()  {
            return new FileRepositoryOptions(this);
        }

    }
}
