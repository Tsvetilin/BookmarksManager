package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Response;
import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.Logger;
import bg.sofia.uni.fmi.mjt.bookmarks.server.sessions.Session;
import bg.sofia.uni.fmi.mjt.bookmarks.server.utils.IdGenerator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server extends Thread {

    private final String host;
    private final int port;
    private final Logger logger;
    private final ByteBuffer messageBuffer;

    private CommandExecutor commandExecutor;
    private boolean isStarted = true;

    public Server(ServerOptions options) {
        this.port = options.port();
        this.host = options.host();
        this.logger = options.logger();
        this.messageBuffer = ByteBuffer.allocate(options.bufferSize());
        this.commandExecutor = CommandExecutor.configure(options.sessionStore(), options.context(), options.logger());
    }

    @Override
    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(host, port));
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (isStarted) {
                int readyChannels = selector.select();
                if (readyChannels == 0) {
                    continue;
                }

                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();

                        messageBuffer.clear();
                        int r = socketChannel.read(messageBuffer);
                        if (r <= 0) {
                            logger.logInfo(
                                "Nothing to read, closing channel for client " + socketChannel.getRemoteAddress());
                            socketChannel.close();
                            continue;
                        }

                        handleKeyIsReadable(key, messageBuffer);
                    } else if (key.isAcceptable()) {
                        handleKeyIsAcceptable(selector, key);
                    }

                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            String traceId = IdGenerator.generateId();
            logger.logError(
                "There is a problem with the server socket: " + e.getMessage() + ". Trace id: " + traceId);
            logger.logException(e, traceId);
        }

        logger.logInfo("Server stopped");
    }

    public void stopServer() {
        isStarted = false;
    }

    private void handleKeyIsReadable(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        buffer.flip();
        String message = new String(buffer.array(), 0, buffer.limit()).trim();
        logger.logInfo("Message received from client " + socketChannel.getRemoteAddress() + " : " + message);
        Response response = commandExecutor.execute(message, new Session(socketChannel, null));
        buffer.clear();
        buffer.put(response.getDataMessage().getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        logger.logInfo(
            "Response sent to client " + socketChannel.getRemoteAddress() + " : " + response.getDataMessage());
    }

    private void handleKeyIsAcceptable(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
        logger.logInfo("Connection accepted from client " + accept.getRemoteAddress());
    }

    // TODO: remove
    private void disconnect(SelectionKey key) throws IOException {
        key.channel().close();
        key.cancel();
    }
}
