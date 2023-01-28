package bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.DIContainer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.AuthenticatedCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.UrlShortenerException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.IdGenerator;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

import java.util.List;

public class ImportFromChromeCommand extends AuthenticatedCommand {

    private static final String CHROME_GROUP = "Chrome";
    private final List<String> urls;

    public ImportFromChromeCommand(List<String> urls) {
        this.urls = urls;
        Nullable.throwIfNull(urls);
    }

    @Override
    protected Response authenticatedExecute() {

        if (user.getGroups().stream().noneMatch(x -> x.getName().equals(CHROME_GROUP))) {
            context.groups().add(new Group(IdGenerator.generateId(), CHROME_GROUP, user));
        }

        BookmarksService service = DIContainer.request(BookmarksService.class);

        urls.forEach(x -> {
            try {
                context.bookmarks().add(service.generateBookmark(x, CHROME_GROUP, false, user));
            } catch (InvalidBookmarkException | UrlShortenerException e) {
                logger.logException(e);
            }
        });

        return new Response("Successfully imported bookmarks from Chrome.", ResponseStatus.OK);
    }
}