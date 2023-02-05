package bg.sofia.uni.fmi.mjt.bookmarks.client.chrome;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChromeService {

    public static String getBookmarks() {
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

        try (var br = Files.newBufferedReader(Path.of(path))) {
            Gson gson = new Gson();
            ChromeImport = gson.fromJson(br, ChromeImport.class);

            BookmarkCategories root = chromeImport.getRoot();

            root.getBookmarkBar().getChildren().stream()
                .map(ChromeBookmark::getUrl)
                .forEach(url -> addTo("Chrome", url, caller));

        } catch (IOException e) {
            String logMsg = BookmarkManager.class + " " + e.getMessage();
            Dispatcher.logger().log(Level.WARN, LocalDateTime.now(), logMsg);
            Dispatcher.logger().log(Level.WARN, LocalDateTime.now(), Arrays.toString(e.getStackTrace()));
        }

        return new Response<>(Status.OK, "Chrome import completed. Bookmarks imported to group Chrome.");
    }
}
