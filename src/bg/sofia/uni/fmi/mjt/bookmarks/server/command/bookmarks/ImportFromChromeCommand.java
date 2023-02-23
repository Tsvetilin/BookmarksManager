package bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.DIContainer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.AuthenticatedCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandType;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.bookmarks.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.IdGeneratorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;

public class ImportFromChromeCommand extends AuthenticatedCommand {

    private static final String CHROME_GROUP = "Chrome";
    private final String url;
    private final IdGeneratorService idGenerator;
    private final BookmarksService bookmarksService;

    public ImportFromChromeCommand(String url) {
        this.url = url;

        this.idGenerator = DIContainer.request(IdGeneratorService.class);
        this.bookmarksService = DIContainer.request(BookmarksService.class);


        Nullable.throwIfNull(url);
    }

    @Override
    protected Response authenticatedExecute() {

        var chromeGroup =
            user.getGroups()
                .stream()
                .filter(x -> x.getName().equals(CHROME_GROUP))
                .findFirst()
                .orElse(new Group(idGenerator.generateId(), CHROME_GROUP, user));

        if (user.getGroups().stream().noneMatch(x -> x == chromeGroup)) {
            context.groups().add(chromeGroup);
        }


        try {
            context.bookmarks().add(bookmarksService.generateBookmark(url, chromeGroup, false, user));
        } catch (InvalidBookmarkException e) {
            String traceId = idGenerator.generateId();
            logger.logError("Server error on importing bookmarks from chrome. Trace id: " + traceId);
            logger.logException(e, traceId);
        }

        return new Response("Successfully imported bookmark from Chrome.", ResponseStatus.OK);
    }


    @Override
    public CommandType getType() {
        return CommandType.IMPORT_FROM_CHROME;
    }
}