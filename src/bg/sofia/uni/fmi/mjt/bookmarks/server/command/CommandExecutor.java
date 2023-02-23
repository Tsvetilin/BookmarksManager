package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.Logger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.DatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions.Session;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions.SessionStore;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.SecureString;

public class CommandExecutor {

    private final SessionStore sessionStore;
    private final DatabaseContext context;
    private final Logger logger;

    public static CommandExecutor configure(SessionStore sessionStore, DatabaseContext context, Logger logger) {
        return new CommandExecutor(sessionStore, context, logger);
    }

    private CommandExecutor(SessionStore sessionStore, DatabaseContext context, Logger logger) {
        this.sessionStore = sessionStore;
        this.context = context;
        this.logger = logger;
    }


    public Response execute(SecureString request, Session session) {
        return CommandParser.parse(request).addDependencies(sessionStore, context).addSessionContext(session)
            .addLogger(logger).execute();
    }
}
