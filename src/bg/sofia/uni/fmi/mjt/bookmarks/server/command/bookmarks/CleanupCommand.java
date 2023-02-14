package bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.DIContainer;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.AuthenticatedCommand;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.BookmarkValidationException;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.IdGenerator;

public class CleanupCommand extends AuthenticatedCommand {

    @Override
    protected Response authenticatedExecute() {
        BookmarksService service = DIContainer.request(BookmarksService.class);
        user.getBookmarks().stream().filter(x -> {
                    try {
                        return !service.validateUrl(x.getUrl());
                    } catch (BookmarkValidationException e) {
                        String traceId = IdGenerator.generateId();
                        logger.logError("Server error on cleaning up bookmarks. Trace id: " + traceId);
                        logger.logException(e, IdGenerator.generateId());
                        return false;
                    }
                }
            )
            .toList()
            .forEach(x -> context.bookmarks().remove(x.getKey()));

        logger.logInfo("Bookmarks cleaned up for user " + user.getUsername());
        return new Response("Bookmarks cleaned up successfully.", ResponseStatus.OK);
    }

}
