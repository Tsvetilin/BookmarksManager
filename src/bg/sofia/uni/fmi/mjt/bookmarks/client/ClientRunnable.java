package bg.sofia.uni.fmi.mjt.bookmarks.client;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientRunnable implements Runnable {

    private final BufferedReader reader;

    public ClientRunnable(BufferedReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        String line;
        try {
            while (true) {
                if ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[ Disconnected ] Connection is closed, communication terminated.");
        }

    }

}