package org.example.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;


public class StoreClientUDP {

    private static final String HOST = "localhost";
    private static final int    PORT = 54321;
    private static final int    TIMEOUT_MS = 1500;
    private static final int    MAX_RETRY  = 3;

    public static void main(String[] args) throws Exception {

        InetAddress serverAddr = InetAddress.getByName(HOST);
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(TIMEOUT_MS);

            for (int i = 0; i < 10; i++) {
                String msg = "Packet-" + i;
                byte[] data = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(data, data.length, serverAddr, PORT);

                int attempt = 0;
                boolean delivered = false;

                while (attempt < MAX_RETRY && !delivered) {
                    socket.send(packet);
                    System.out.println("→ " + msg + " (try " + (attempt + 1) + ")");

                    try {
                        byte[] ackBuf = new byte[1024];
                        DatagramPacket ackPacket = new DatagramPacket(ackBuf, ackBuf.length);
                        socket.receive(ackPacket);                 // чекаємо ACK

                        String ack = new String(ackPacket.getData(), 0, ackPacket.getLength());
                        System.out.println("   ← " + ack);
                        delivered = true;
                    } catch (java.net.SocketTimeoutException e) {
                        attempt++;
                        if (attempt == MAX_RETRY)
                            System.out.println("   !! no ACK, giving up");
                    }
                }
                TimeUnit.SECONDS.sleep(1);
            }
        }
    }
}
