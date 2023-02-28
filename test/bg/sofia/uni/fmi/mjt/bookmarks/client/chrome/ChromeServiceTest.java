package bg.sofia.uni.fmi.mjt.bookmarks.client.chrome;

import bg.sofia.uni.fmi.mjt.bookmarks.client.exceptions.ChromeException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


public class ChromeServiceTest {

    @Test
    @Disabled
    void testGetBookmarks() {
        assertDoesNotThrow(()->new ChromeService(new Gson()).getBookmarks(new StringReader("")));
    }
}
