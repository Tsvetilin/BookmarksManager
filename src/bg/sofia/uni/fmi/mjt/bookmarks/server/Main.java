package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.BookmarksLogger;

public class Main {

    public static void main(String... args) {

        // TODO: input handling
        new Server(25565,new BookmarksLogger()).start();
    }

}
