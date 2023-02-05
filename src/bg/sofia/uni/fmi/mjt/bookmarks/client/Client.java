package bg.sofia.uni.fmi.mjt.bookmarks.client;

import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Request;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class Client {

    private static final String DISCONNECT_COMMAND = "disconnect";
    private static final String STATUS_COMMAND = "status";
    private static final String IMPORT_CHROME_COMMAND = "import-from-chrome";
    private static final String COMMAND_PROMPT = "=> ";

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Client() {
        this(SERVER_HOST, SERVER_PORT);
    }


    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(host, port));

            System.out.println("[ Connected ] Connected to the server. Type help for more info.");

            new Thread(new ClientMessageReaderRunnable(reader)).start();

            while (true) {
                System.out.print(COMMAND_PROMPT);
                String command = scanner.nextLine().trim();

                if (DISCONNECT_COMMAND.equalsIgnoreCase(command)) {
                    break;
                }

                if (STATUS_COMMAND.equalsIgnoreCase(command)) {
                    System.out.println("[ Connected ]");
                    continue;
                }

                if (IMPORT_CHROME_COMMAND.equalsIgnoreCase(command)) {
                    command += importChrome();
                }

                writer.println(new Request(command).getDataMessage());
            }

            System.out.println("[ Disconnected ]");
        } catch (IOException e) {
            System.err.println(
                "[ Error ] An error occurred in the communication with the server. Connection is closed.");
        }
    }
}
