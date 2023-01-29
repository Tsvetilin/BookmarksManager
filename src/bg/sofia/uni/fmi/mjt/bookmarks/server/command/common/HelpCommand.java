package bg.sofia.uni.fmi.mjt.bookmarks.server.command.common;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.ResponseStatus;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandBase;

public class HelpCommand extends CommandBase {

    private static final String HELP_PROMPT = "Help pls";

    @Override
    public Response execute() {
        return new Response(HELP_PROMPT, ResponseStatus.OK);
    }
}