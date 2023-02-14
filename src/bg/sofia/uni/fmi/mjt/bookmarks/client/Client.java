package bg.sofia.uni.fmi.mjt.bookmarks.client;

import bg.sofia.uni.fmi.mjt.bookmarks.client.exceptions.ChromeException;
import bg.sofia.uni.fmi.mjt.bookmarks.client.chrome.ChromeService;
import bg.sofia.uni.fmi.mjt.bookmarks.client.exceptions.ExceptionLogHandler;
import bg.sofia.uni.fmi.mjt.bookmarks.contracts.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client extends Thread {

    private static final String DISCONNECT_COMMAND = "disconnect";
    private static final String STATUS_COMMAND = "status";
    private static final String IMPORT_CHROME_COMMAND = "import-from-chrome";
    private static final String COMMAND_PROMPT = "=> ";

    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open();
             BufferedReader reader = new BufferedReader(Channels.newReader(socketChannel, StandardCharsets.UTF_8));
             PrintWriter writer = new PrintWriter(Channels.newWriter(socketChannel, StandardCharsets.UTF_8), true);
             Scanner scanner = new Scanner(System.in)) {

            socketChannel.connect(new InetSocketAddress(host, port));

            System.out.println("[ Connected ] Connected to the server. Type help for more info.");

            var messageReaderThread = new Thread(new ClientMessageReader(reader));
            messageReaderThread.start();

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
                    try {
                        command += " " + ChromeService.getBookmarks();
                    } catch (ChromeException e) {
                        System.out.println("[ Error ] Error parsing chrome bookmarks from your client.");
                        ExceptionLogHandler.logException(e);
                    }
                }

                writer.println(new Request(command).getDataMessage());
            }

            System.out.println("[ Disconnected ]");
            messageReaderThread.interrupt();
        } catch (IOException e) {
            ExceptionLogHandler.logException(e);
            System.err.println(
                "[ Error ] An error occurred in the communication with the server. Connection is closed.");
        }
    }
}
