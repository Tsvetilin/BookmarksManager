package bg.sofia.uni.fmi.mjt.bookmarks.server.services.external;

import com.google.gson.annotations.SerializedName;

public record ShortUrlResult(@SerializedName("created_at") String createdAt, String id, String link) {

}
