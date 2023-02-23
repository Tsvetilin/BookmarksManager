package bg.sofia.uni.fmi.mjt.bookmarks.client.chrome;

import bg.sofia.uni.fmi.mjt.bookmarks.client.exceptions.ChromeException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class ChromeServiceTest {

    @Test
    @Disabled
    void testGetBookmarks() {
        try {
            var result = ChromeService.getBookmarks();
            fail(result.stream().filter(x -> !x.startsWith("http")).findFirst().get());
            assertEquals(result.size(), result.stream().filter(x -> x.startsWith("http")).map(String::trim).toList().size(),
                "All must be valid urls");
        } catch (ChromeException e) {
            // ignored;
        }
    }
}
