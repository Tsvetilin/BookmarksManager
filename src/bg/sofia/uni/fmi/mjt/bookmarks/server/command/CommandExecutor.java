package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.DatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.sessions.SessionStore;

public class CommandExecutor {

    private final SessionStore sessionStore;
    private final DatabaseContext context;

    public static CommandExecutor configure(SessionStore sessionStore, DatabaseContext context) {
        return new CommandExecutor(sessionStore, context);
    }

    private CommandExecutor(SessionStore sessionStore, DatabaseContext context) {

        this.sessionStore = sessionStore;
        this.context = context;
    }


    public Response execute(String cmd) {

        return new Response("", ResponseStatus.OK);
    }
}
