package bg.sofia.uni.fmi.mjt.bookmarks.server.command;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;

public interface Command {
    Response execute();
}
