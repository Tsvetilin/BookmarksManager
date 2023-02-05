package bg.sofia.uni.fmi.mjt.bookmarks.client.chrome;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class ChromeService {

    public static String getBookmarks() throws ChromeException {
        String osName = System.getProperty("os.name");
        String osUser = System.getProperty("user.name");

        String path;
        if (osName.toLowerCase().contains("linux")) {
            path = String.format("/home/%s/.config/google-chrome/Default/Bookmarks", osUser);
        } else if (osName.toLowerCase().contains("windows")) {
            path = "AppData\\Local\\Google\\Chrome\\User Data\\Default\\Bookmarks";
        } else if (osName.toLowerCase().contains("mac")) {
            path = String.format("/Users/%s/Library/Application\\ Support/Google/Chrome/Bookmarks", osUser);
        } else {
            throw new UnsupportedOperationException("Cannot extract bookmarks.");
        }

        Gson gson = new Gson();

        try (var reader = Files.newBufferedReader(Path.of(path))) {
            return gson
                .fromJson(reader, ChromeBookmarks.class)
                .getRoot()
                .getBookmarkBar()
                .getChildren()
                .stream()
                .map(ChromeBookmark::getUrl)
                .collect(Collectors.joining(","));
        } catch (IOException e) {
            throw new ChromeException();
        }
    }
}
