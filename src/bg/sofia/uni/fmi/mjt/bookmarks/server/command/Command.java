package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.DatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.sessions.Session;
import bg.sofia.uni.fmi.mjt.bookmarks.server.sessions.SessionStore;

public interface Command {
    Response execute();

    Command addDependencies(SessionStore sessionStore, DatabaseContext context);

    Command addSessionContext(Session session);
}
