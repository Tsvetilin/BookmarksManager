package bg.sofia.uni.fmi.mjt.bookmarks.server.command.account;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandBase;

public class LogoutCommand extends CommandBase {

    @Override
    public Response execute() {

        if (!sessionStore.hasSession(session)) {
            logger.logInfo("Logout attempt for not logged user.");
            return new Response("User not logged in.", ResponseStatus.ERROR);
        }

        sessionStore.remove(session);

        logger.logInfo("User logged out: " + session.user().getUsername());
        return new Response("Logged out successfully.", ResponseStatus.OK);
    }
}