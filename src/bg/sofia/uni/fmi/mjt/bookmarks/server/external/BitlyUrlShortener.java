package bg.sofia.uni.fmi.mjt.bookmarks.server.external;

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

    private final Gson gson;
    private final HttpClient client;

    public BitlyUrlShortener(HttpClient client) {
        this.client = client;
        this.gson = new Gson();
    }

    @Override
    public String shorten(String url) {
        Objects.requireNonNull(url, "Url is null!");

        String body = String.format("{\"long_url\":\"%s\"}", url);

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
            throw new RuntimeException("", e);
        }

        if (response.statusCode() != HttpURLConnection.HTTP_CREATED &&
            response.statusCode() != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("");
        }

        ShortUrlResult shorten = gson.fromJson(response.body(), ShortUrlResult.class);

        return shorten.link();
    }


}
