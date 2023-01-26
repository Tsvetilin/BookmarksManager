package bg.sofia.uni.fmi.mjt.bookmarks.server;

import bg.sofia.uni.fmi.mjt.bookmarks.server.command.CommandExecutor;
import bg.sofia.uni.fmi.mjt.bookmarks.server.logging.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server {

    private static final int SERVER_PORT = 8080;
    private static final int BUFFER_SIZE = 1024;
    private static final String SERVER_HOST = "localhost";

    private final int port;
    private final Logger logger;
    private final ByteBuffer messageBuffer;

    private CommandExecutor commandExecutor;


    private boolean isStarted = true;

    // TODO: add builder for configuration
    public Server(int port, Logger logger) {
        this.port = port;
        this.logger = logger;
        this.messageBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    }

    public Server(Logger logger) {
        this(SERVER_PORT, logger);
    }

    public void start() {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.bind(new InetSocketAddress(SERVER_HOST, port));
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
                            logger.logInfo("Nothing to read, closing channel -> " + socketChannel.getRemoteAddress());
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
            logger.logError("There is a problem with the server socket: " + e.getMessage());
            logger.logException(e);
        }

        logger.logInfo("Server stopped");
    }

    public void stop() {
        isStarted = false;
    }

    private void handleKeyIsReadable(SelectionKey key, ByteBuffer buffer) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        buffer.flip();
        String message = new String(buffer.array(), 0, buffer.limit()).trim();
        logger.logInfo("Message [" + message + "] received from client " + socketChannel.getRemoteAddress());
        String response = commandExecutor.execute(message);
        logger.logInfo("Sending response to client: " + response);
        response += System.lineSeparator();
        buffer.clear();
        buffer.put(response.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    private void handleKeyIsAcceptable(Selector selector, SelectionKey key) throws IOException {
        ServerSocketChannel sockChannel = (ServerSocketChannel) key.channel();
        SocketChannel accept = sockChannel.accept();
        accept.configureBlocking(false);
        accept.register(selector, SelectionKey.OP_READ);
        logger.logInfo("Connection accepted from client " + accept.getRemoteAddress());
    }

    private void disconnect(SelectionKey key) throws IOException {
        key.channel().close();
        key.cancel();
    }
}
