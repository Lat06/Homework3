package org.example.tcp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;


public class StoreClientTCP {

    private static final String HOST = "localhost";
    private static final int    PORT = 12345;

    public static void main(String[] args) throws Exception {

        while (true) {
            try (Socket socket = new Socket(HOST, PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                System.out.println("Connected to server");
                int counter = 0;

                while (true) {                 // поки з’єднання живе
                    String msg = "Msg-" + counter++ + " @ " + LocalTime.now();
                    out.println(msg);          // надсилаємо
                    String ack = in.readLine();// чекаємо підтвердження
                    System.out.println("→ " + msg + " | ← " + ack);
                    TimeUnit.SECONDS.sleep(2);
                }

            } catch (Exception e) {            // обрив чи недоступність сервера
                System.out.println("Server unavailable: " + e.getMessage());
                TimeUnit.SECONDS.sleep(3);     // пауза перед повторною спробою
            }
        }
    }
}
