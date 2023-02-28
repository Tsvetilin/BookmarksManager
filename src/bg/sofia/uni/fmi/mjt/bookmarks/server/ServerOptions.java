package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.services.identity.IdGeneratorService;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.DefaultLogger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.logging.Logger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.persistence.DatabaseContext;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions.DefaultSessionStore;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.sessions.SessionStore;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.Nullable;
import bg.sofia.uni.fmi.mjt.bookmarks.server.services.Service;
import com.google.gson.Gson;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class ServerOptions {

    private static final int DEFAULT_BUFFER_SIZE = 8192;
    private static final String DEFAULT_SERVER_HOST = "localhost";
    private static final int DEFAULT_SERVER_PORT = 8080;

    private final int port;
    private final String host;
    private final int bufferSize;
    private final DatabaseContext context;
    private final Logger logger;
    private final SessionStore sessionStore;

    private static final int ALLOWED_PORT_LB = 1000;
    private final HttpClient httpClient;
    private final Gson gson;

    private ServerOptions(ServerOptionsBuilder builder) {
        DIContainer.clear();

        Nullable.throwIfNull(builder.context);

        this.port = builder.port > ALLOWED_PORT_LB ? builder.port : DEFAULT_SERVER_PORT;
        this.bufferSize = builder.bufferSize > 0 ? builder.bufferSize : DEFAULT_BUFFER_SIZE;
        this.host = Nullable.orDefault(builder.host, DEFAULT_SERVER_HOST);
        this.context = builder.context;
        this.httpClient = Nullable.orDefault(builder.httpClient, HttpClient.newHttpClient());
        this.logger = Nullable.orDefault(builder.logger, DefaultLogger.getDefaultLogger());
        this.sessionStore = Nullable.orDefault(builder.sessionStore, new DefaultSessionStore());
        this.gson = Nullable.orDefault(builder.gson, new Gson());
        DIContainer.registerSingleton(DatabaseContext.class, context);
        DIContainer.registerSingleton(Logger.class, logger);
        DIContainer.registerSingleton(SessionStore.class, sessionStore);
        DIContainer.registerUnchecked(HttpClient.class, httpClient);
        DIContainer.registerUnchecked(Gson.class, gson);
        for (var service : builder.serviceListSingleton.entrySet()) {
            DIContainer.registerSingleton(service.getKey(), service.getValue());
        }

        for (var service : builder.serviceListTransient.entrySet()) {
            DIContainer.registerTransient(service.getKey(), service.getValue().getKey(), service.getValue().getValue());
        }
    }

    public static ServerOptionsBuilder create(int port) {
        return new ServerOptionsBuilder(port);
    }

    public int port() {
        return port;
    }

    public String host() {
        return host;
    }

    public int bufferSize() {
        return bufferSize;
    }

    public DatabaseContext context() {
        return context;
    }

    public Logger logger() {
        return logger;
    }

    public SessionStore sessionStore() {
        return sessionStore;
    }

    public IdGeneratorService idGenerator() {
        return null;
    }

    public static class ServerOptionsBuilder {

        private final int port;
        private int bufferSize = 0;
        private String host;
        private DatabaseContext context;
        private Logger logger;
        private SessionStore sessionStore;
        private final Map<Type, Service> serviceListSingleton;
        private final Map<Type, Map.Entry<Type, Class[]>> serviceListTransient;
        private HttpClient httpClient;
        private Gson gson;

        private ServerOptionsBuilder(int port) {
            this.port = port;

            this.serviceListSingleton = new HashMap<>();
            this.serviceListTransient = new HashMap<>();
        }

        public ServerOptionsBuilder setHost(String host) {
            this.host = host;
            return this;
        }

        public ServerOptionsBuilder setBufferSize(int size) {
            this.bufferSize = size;
            return this;
        }

        public ServerOptionsBuilder addDatabaseContext(DatabaseContext context) {
            this.context = context;
            return this;
        }

        public ServerOptionsBuilder addLogger(Logger logger) {
            this.logger = logger;
            return this;
        }

        public ServerOptionsBuilder addSessionStore(SessionStore store) {
            this.sessionStore = store;
            return this;
        }

        public ServerOptionsBuilder addSingletonService(Type type, Service service) {
            this.serviceListSingleton.put(type, service);
            return this;
        }


        public ServerOptionsBuilder addTransientService(Type registerType, Type implementationType, Class[] typeArgs) {
            this.serviceListTransient.put(registerType, Map.entry(implementationType, typeArgs));
            return this;
        }

        public ServerOptionsBuilder addHttpClient(HttpClient httpClient) {
            this.httpClient = httpClient;
            return this;
        }

        public ServerOptionsBuilder addSerializer(Gson gson) {
            this.gson = gson;
            return this;
        }

        public ServerOptions build() {
            return new ServerOptions(this);
        }
    }
}
