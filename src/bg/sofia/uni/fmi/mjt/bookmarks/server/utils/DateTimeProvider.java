package bg.sofia.uni.fmi.mjt.bookmarks.server.utils;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime getCurrentTime();
}
