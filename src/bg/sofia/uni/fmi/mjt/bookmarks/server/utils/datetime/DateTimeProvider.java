package bg.sofia.uni.fmi.mjt.bookmarks.server.utils.datetime;

import java.time.LocalDateTime;

public interface DateTimeProvider {
    LocalDateTime getCurrentTime();
}
