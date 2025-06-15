package org.example.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class StoreServerTCP {

    private static final int PORT = 12345;

    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP-server started on port " + PORT);

            while (true) {                                 // нескінченне очікування клієнтів
                Socket clientSocket = server.accept();     // блокуючий виклик
                new Thread(() -> handleClient(clientSocket)).start();
            }
        }
    }

    private static void handleClient(Socket socket) {
        System.out.println("Client connected: " + socket.getRemoteSocketAddress());
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter    out = new PrintWriter(socket.getOutputStream(), true)) {

            String line;
            while ((line = in.readLine()) != null) {       // читаємо доти, поки не закриють потік
                System.out.println("← " + line);
                out.println("ACK: " + line);               // повертаємо підтвердження
            }
        } catch (Exception e) {
            System.out.println("Client error: " + e.getMessage());
        } finally {
            try { socket.close(); } catch (Exception ignored) {}
            System.out.println("Client disconnected");
        }
    }
}
