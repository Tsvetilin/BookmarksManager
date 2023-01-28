package bg.sofia.uni.fmi.mjt.bookmarks.server.sessions;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;

public class DefaultSessionStore implements SessionStore {
    @Override
    public boolean hasSession(Session session) {
        return false;
    }

    @Override
    public User getUser(Session session) {
        return null;
    }

    @Override
    public void Register(Session session) {

    }

    @Override
    public void remove(Session session) {

    }
}
