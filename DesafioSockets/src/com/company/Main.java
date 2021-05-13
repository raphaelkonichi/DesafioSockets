package com.company;


import Utils.Server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    private static ServerSocket server;
    private static int PORT = 8764;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException{

        try {
            server = new ServerSocket(PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Server Socket on port = " + server.getLocalPort());

        while (true) {
            try {
                Socket socket = server.accept();
                System.out.println("Client Accepted!");
                System.out.println("HOSTNAME = " + socket.getInetAddress().getHostName());
                System.out.println("HOST ADDRESS = " + socket.getInetAddress().getHostAddress());
                System.out.println("LOCAL PORT = " + socket.getLocalPort());
                System.out.println("PORT = " + socket.getPort() + "\r\n\r\n");
                System.out.println("send 'close' to close the connection.");
                new Server(socket).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
