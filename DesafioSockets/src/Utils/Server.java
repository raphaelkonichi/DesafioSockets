package Utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Server extends Thread {
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream stream = socket.getInputStream()) {
            boolean active = true;
            while (active) {
                if (stream.available() != 0) {
                    byte[] streamData = new byte[stream.available()];
                    stream.read(streamData);
                    String readData = new String(streamData);
                    if (readData.equals("close")) {
                        System.out.println("Connection closed.");
                        active = false;
                    } else {
                        ServerPrinter.getInstance().addMessage(new String(streamData));
                    }
                }
                Thread.sleep(10);
            }
            socket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}