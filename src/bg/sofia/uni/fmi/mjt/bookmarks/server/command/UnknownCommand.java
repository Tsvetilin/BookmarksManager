package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;

public class UnknownCommand implements Command {
    @Override
    public Response execute() {
        return new Response("Unknown command specified", ResponseStatus.ERROR);
    }
}
