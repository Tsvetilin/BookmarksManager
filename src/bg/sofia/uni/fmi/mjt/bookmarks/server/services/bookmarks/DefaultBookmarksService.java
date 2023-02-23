package bg.sofia.uni.fmi.mjt.bookmarks.server.services.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.HtmlExtractorException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.external.UrlShortener;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.htmlextractor.HtmlExtractorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.IdGeneratorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.StemmingService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stopwords.StopwordsService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import org.jsoup.nodes.Document;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DefaultBookmarksService implements BookmarksService {
    private static final String SPLIT_REGEX = "[\\s\\p{IsPunctuation}]+";
    private static final int MAX_KEYWORDS = 25;
    private final HttpClient client;
    private final StopwordsService stopwordsService;
    private final StemmingService stemmingService;
    private final IdGeneratorService idGeneratorService;
    private final UrlShortener shortener;
    private final HtmlExtractorService htmlExtractorService;

    public DefaultBookmarksService(HttpClient client,
                                   StopwordsService stopwordsService,
                                   StemmingService stemmingService,
                                   IdGeneratorService idGeneratorService,
                                   UrlShortener shortener,
                                   HtmlExtractorService htmlExtractorService) {
        this.client = client;
        this.stopwordsService = stopwordsService;
        this.stemmingService = stemmingService;
        this.idGeneratorService = idGeneratorService;
        this.shortener = shortener;
        this.htmlExtractorService = htmlExtractorService;
    }

    @Override
    public Bookmark generateBookmark(String url, Group group, boolean shortened, User user)
        throws InvalidBookmarkException {
        Nullable.throwIfAnyNull(url, group, user);

        validateUrl(url);

        String shortenedUrl = "";

        if (shortened) {
            try {
                shortenedUrl = shortener.shorten(url);
            } catch (UrlShortenerException e) {
                throw new InvalidBookmarkException(e);
            }
        }

        Document htmlDocument;
        try {
            htmlDocument = htmlExtractorService.get(url);
        } catch (HtmlExtractorException e) {
            throw new RuntimeException(e);
        }


        var tags = Arrays
            .stream(htmlDocument
                .body()
                .text()
                .split(SPLIT_REGEX))
            .map(String::trim)
            .map(String::toLowerCase)
            .map(stemmingService::stemWord)
            .filter(Predicate.not(stopwordsService::isStopword))
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .map(Map.Entry::getKey)
            .limit(MAX_KEYWORDS)
            .toList();

        return new Bookmark(
            idGeneratorService.generateId(),
            url,
            shortenedUrl,
            htmlDocument.title(),
            tags,
            user,
            group);
    }

    @Override
    public boolean validateUrl(String url) throws InvalidBookmarkException {
        try {
            return
                client.send(HttpRequest.newBuilder().uri(URI.create(url)).build(), HttpResponse.BodyHandlers.ofString())
                    .statusCode() !=
                    HttpURLConnection.HTTP_NOT_FOUND;
        } catch (Exception e) {
            throw new InvalidBookmarkException(e);
        }
    }
}
