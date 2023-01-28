package bg.sofia.uni.fmi.mjt.bookmarks.server.command.bookmarks;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.AuthenticatedCommand;

public class CleanupCommand extends AuthenticatedCommand {
    @Override
    protected Response authenticatedExecute() {
        return null;
    }
}
