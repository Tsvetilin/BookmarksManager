package bg.sofia.uni.fmi.mjt.bookmarks.server.external;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class BitlyUrlShortener implements UrlShortener {

    private static final String BITLY_API_URL = "https://api-ssl.bitly.com/v4/shorten";
    private static final String AUTH_HEADER_VALUE = "INSERT_API_KEY_HERE";
    private static final String REQUEST_BODY_PATTERN = "{\"long_url\":\"%s\"}";

    private final Gson gson;
    private final HttpClient client;

    public BitlyUrlShortener(HttpClient client) {
        this.client = client;
        this.gson = new Gson();
    }

    @Override
    public String shorten(String url) throws UrlShortenerException {
        Nullable.throwIfNull(url);

        String body = String.format(REQUEST_BODY_PATTERN, url);

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(BITLY_API_URL))
            .setHeader("Content-Type", "application/json")
            .setHeader("Authorization", AUTH_HEADER_VALUE)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new UrlShortenerException("", e);
        }

        if (response.statusCode() != HttpURLConnection.HTTP_CREATED &&
            response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new UrlShortenerException("");
        }

        ShortUrlResult shorten = gson.fromJson(response.body(), ShortUrlResult.class);

        return shorten.link();
    }


}
