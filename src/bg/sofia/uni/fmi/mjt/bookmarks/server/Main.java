package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.external.BitlyUrlShortener;
import bg.sofia.uni.fmi.mjt.bookmarks.server.external.UrlShortener;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.DefaultLogger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.Severity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.DefaultConsoleProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.providers.DefaultFileProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.FileDatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.FileRepository;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.FileRepositoryOptions;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.DefaultBookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.sessions.DefaultSessionStore;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.datetime.DefaultDateTimeProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.hasher.DefaultPasswordHasher;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.hasher.PasswordHasher;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    private static final int PORT = 8080;

    public static void main(String... args) throws InterruptedException {
        var options = ServerOptions
            .create(PORT)
            .addSessionStore(new DefaultSessionStore())
            .addLogger(
                DefaultLogger
                    .configure()
                    .addConsoleLogging(Severity.INFO)
                    .configureConsoleProvider(new DefaultConsoleProvider())
                    .addFileLogging(Severity.INFO)
                    .configureFileProvider(new DefaultFileProvider())
                    .configureDateTimeProvider(new DefaultDateTimeProvider())
                    .build()
            )
            .addDatabaseContext(
                new FileDatabaseContext(
                    new FileRepository<>(FileRepositoryOptions.create("./db/users.json").build()),
                    new FileRepository<>(FileRepositoryOptions.create("./db/bookmarks.json").build()),
                    new FileRepository<>(FileRepositoryOptions.create("./db/groups.json").build())
                )
            )
            .addService(PasswordHasher.class, new DefaultPasswordHasher(new SecureRandom()))
            .addService(UrlShortener.class, new BitlyUrlShortener(HttpClient.newHttpClient()))
            .addService(BookmarksService.class, new DefaultBookmarksService(HttpClient.newHttpClient()))
            .build();

        var server = new Server(options);

        var scanner = new Scanner(System.in);
        System.out.println("<Bookmarks manager server>");
        System.out.println("Available commands: ");
        System.out.println("start : starts the server");
        System.out.println("stop : stops the server");
        System.out.println();

        while (true) {
            System.out.print(">> ");
            String cmd = scanner.nextLine();

            switch (cmd) {
                case "start": {
                    server.start();
                    System.out.println("Starting server on port " + PORT + "...");
                    break;
                }

                case "stop": {
                    System.out.println("Stopping server...");
                    server.stopServer();
                    server.join();
                    return;
                }

                default: {
                    System.out.println("Invalid command.");
                    break;
                }
            }

        }
    }

}
