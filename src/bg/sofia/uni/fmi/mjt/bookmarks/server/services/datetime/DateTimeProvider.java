package bg.sofia.uni.fmi.mjt.bookmarks.server.services.datetime;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

import java.time.LocalDateTime;

public interface DateTimeProvider extends Service {
    LocalDateTime getCurrentTime();
}
