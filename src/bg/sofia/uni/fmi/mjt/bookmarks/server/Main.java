package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.DefaultLogger;

public class Main {

    private static final int DEFAULT_PORT = 25565;

    // TODO: Exception logging trace id
    public static void main(String... args) {

        // TODO: input handling
        new Server(DEFAULT_PORT, DefaultLogger.getDefaultLogger()).start();
    }

}
