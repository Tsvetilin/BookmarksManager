package bg.sofia.uni.fmi.mjt.bookmarks.client.chrome;

import bg.sofia.uni.fmi.mjt.bookmarks.client.exceptions.ChromeException;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ChromeService {

    private final Gson gson;

    public ChromeService(Gson gson) {

        this.gson = gson;
    }

    public List<String> getBookmarks(Reader reader) throws ChromeException {
        try (reader) {
            return gson
                .fromJson(reader, ChromeBookmarks.class)
                .roots()
                .getAll()
                .stream()
                .map(ChromeBookmark::url)
                .distinct()
                .toList();
        } catch (IOException e) {
            throw new ChromeException("Error extracting bookmarks from system files.");
        }
    }

    public Reader getFileReader() throws ChromeException {
        String osName = System.getProperty("os.name");

        Path path;
        if (osName.toLowerCase().contains("linux")) {
            path = Path.of("~", ".config", "google-chrome", "Default", "Bookmarks");
        } else if (osName.toLowerCase().contains("windows")) {
            path =
                Path.of(System.getProperty("user.home"), "AppData", "Local", "Google", "Chrome", "User Data", "Default",
                    "Bookmarks");
        } else if (osName.toLowerCase().contains("mac")) {
            path = Path.of("Users", System.getProperty("user.name"), "Library", "Application", "Support", "Google",
                "Chrome", "Bookmarks");
        } else {
            throw new ChromeException("Cannot extract bookmarks. Unknown OS.");
        }

        try {
            return Files.newBufferedReader(path);
        } catch (IOException e) {
            throw new ChromeException("Cannot open file.");
        }
    }
}
