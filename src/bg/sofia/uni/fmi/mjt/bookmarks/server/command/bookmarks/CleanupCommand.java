package bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.DIContainer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.AuthenticatedCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandType;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.InvalidBookmarkException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.bookmarks.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.IdGeneratorService;

public class CleanupCommand extends AuthenticatedCommand {

    private final BookmarksService bookmarksService;
    private final IdGeneratorService idGenerator;

    public CleanupCommand() {
        idGenerator = DIContainer.request(IdGeneratorService.class);
        bookmarksService = DIContainer.request(BookmarksService.class);
    }

    private boolean isValid(Bookmark bookmark) {
        try {
            return !bookmarksService.validateUrl(bookmark.getUrl());
        } catch (InvalidBookmarkException e) {
            String traceId = idGenerator.generateId();
            logger.logError("Server error on cleaning up bookmarks. Trace id: " + traceId);
            logger.logException(e, traceId);
            return false;
        }
    }

    @Override
    protected Response authenticatedExecute() {
        user.getBookmarks()
            .stream()
            .filter(this::isValid)
            .toList()
            .forEach(x -> context.bookmarks().remove(x.getKey()));

        logger.logInfo("Bookmarks cleaned up for user " + user.getUsername());
        return new Response("Bookmarks cleaned up successfully.", ResponseStatus.OK);
    }


    @Override
    public CommandType getType() {
        return CommandType.CLEANUP;
    }

}
