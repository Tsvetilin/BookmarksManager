package bg.sofia.uni.fmi.mjt.bookmarks.server.command.common;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandBase;

public class UnknownCommand extends CommandBase {

    @Override
    public Response execute() {
        return new Response("Unknown command specified", ResponseStatus.ERROR);
    }
}
