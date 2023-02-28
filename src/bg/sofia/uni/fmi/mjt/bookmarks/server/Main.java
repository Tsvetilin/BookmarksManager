package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.bookmarks.BookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.bookmarks.DefaultBookmarksService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.external.BitlyUrlShortener;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.external.UrlShortener;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.DefaultPasswordHasher;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.PasswordHasher;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.strategy.HasherAlgorithmStrategy;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.hasher.strategy.SHA1HasherStrategy;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.htmlextractor.DefaultHtmlExtractorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.htmlextractor.HtmlExtractorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.DefaultIdGeneratorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.IdGeneratorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.strategy.UUIDGeneratorStrategy;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.DefaultLogger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.Severity;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.providers.DefaultConsoleProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.providers.DefaultFileProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Bookmark;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.Group;
import bg.sofia.uni.fmi.mjt.bookmarks.server.models.User;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.FileDatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.FileRepository;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.repository.FileRepositoryOptions;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions.DefaultSessionStore;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.datetime.DefaultDateTimeProvider;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.DefaultStemmingService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.StemmingService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stemming.strategy.SuffixStrippingStrategy;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stopwords.DefaultStopwordsService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.stopwords.StopwordsService;
import com.google.gson.Gson;

import java.net.http.HttpClient;
import java.security.SecureRandom;
import java.util.Scanner;

public class Main {

    private static final int PORT = 8080;
    private static final String BITLY_AUTH_KEY_ENV_VAR_NAME = "bookmarks.bitly.api.key";

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
                    new FileRepository<>(
                        FileRepositoryOptions.create("./db/users.json").build(),
                        String.class,
                        User.class),
                    new FileRepository<>(
                        FileRepositoryOptions.create("./db/bookmarks.json").build(),
                        String.class,
                        Bookmark.class),
                    new FileRepository<>(
                        FileRepositoryOptions.create("./db/groups.json").build(),
                        String.class,
                        Group.class)
                )
            )
            .addHttpClient(HttpClient.newHttpClient())
            .addSingletonService(HasherAlgorithmStrategy.class, new SHA1HasherStrategy(new SecureRandom()))
            .addSingletonService(StopwordsService.class, new DefaultStopwordsService())
            .addSingletonService(StemmingService.class, new DefaultStemmingService(new SuffixStrippingStrategy()))
            .addSingletonService(IdGeneratorService.class,new DefaultIdGeneratorService(new UUIDGeneratorStrategy()))
            .addSingletonService(HtmlExtractorService.class, new DefaultHtmlExtractorService())
            .addTransientService(PasswordHasher.class, DefaultPasswordHasher.class,
                new Class[] {HasherAlgorithmStrategy.class})
            .addSingletonService(UrlShortener.class, new BitlyUrlShortener(HttpClient.newHttpClient(), new Gson(),
                System.getenv(BITLY_AUTH_KEY_ENV_VAR_NAME)))
            .addTransientService(BookmarksService.class, DefaultBookmarksService.class, new Class[] {HttpClient.class,
                StopwordsService.class,StemmingService.class , IdGeneratorService.class, UrlShortener.class})
            .build();

        Thread serverThread = null;
        Server server = null;

        var scanner = new Scanner(System.in);
        System.out.println("<Bookmarks manager server>");
        printCommands();

        while (true) {
            System.out.print(">> ");
            String cmd = scanner.nextLine();

            switch (cmd) {
                case "start" -> {
                    if (server != null) {
                        System.out.println("Server already started. ");
                        break;
                    }

                    server = new Server(options);
                    serverThread = new Thread(server);
                    serverThread.start();

                    System.out.println("Starting server on port " + PORT + "...");
                }

                case "stop" -> {
                    if (server == null) {
                        System.out.println("Server not started yet.");
                        break;
                    }

                    System.out.println("Stopping server...");
                    server.stopServer();
                    serverThread.interrupt();
                    serverThread.join();
                    server = null;
                }

                case "exit" -> {
                    if (server != null) {
                        System.out.println("Server still running. Cannot exit.");
                        break;
                    }

                    return;
                }

                default -> {
                    System.out.println("Invalid command.");
                    printCommands();
                }
            }

        }
    }

        private static void printCommands () {
            System.out.println("Available commands: ");
            System.out.println("start : starts the server");
            System.out.println("stop  : stops the server");
            System.out.println("exit  : exits the server console");
            System.out.println();
        }


    }
