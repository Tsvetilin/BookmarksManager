package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.server.exceptions.command.UnauthenticatedCommandException;

public class AuthenticatedCommand extends CommandBase {

    @Override
    public Response execute()  {
        if(!sessionStore.hasSession(session)){
            throw new UnauthenticatedCommandException();
        }

        return null;
    }
}
