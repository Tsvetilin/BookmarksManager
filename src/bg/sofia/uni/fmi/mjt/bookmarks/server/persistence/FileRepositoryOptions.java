package bg.sofia.uni.fmi.mjt.bookmarks.server.persistence;

import java.io.Reader;
import java.io.Writer;

public class FileRepositoryOptions {

    public static FileRepositoryOptions getDefault() {
        return null;
    }

    private FileRepositoryOptions() {

    }

    Reader getReader() {
        return null;
    }

    Writer getWriter() {
        return null;
    }
}
