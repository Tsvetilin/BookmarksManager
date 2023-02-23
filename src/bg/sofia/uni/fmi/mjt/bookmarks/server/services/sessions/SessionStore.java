package bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions;

import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;

public interface SessionStore extends Service {
    boolean hasSession(Session session); // get by key

    User getUser(Session session); // get by key

    void register(Session session); // add key-user

    void remove(Session session); //remove by key
}
