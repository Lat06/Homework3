package org.example.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;


public class StoreServerUDP {

    private static final int PORT = 54321;

    public static void main(String[] args) throws Exception {
        byte[] buf = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("UDP-server started on port " + PORT);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);                            // чекаємо пакет

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("← " + msg);

                String ack = "ACK:" + msg;
                DatagramPacket ackPacket = new DatagramPacket(
                        ack.getBytes(), ack.length(),
                        packet.getAddress(), packet.getPort());
                socket.send(ackPacket);                            // повертаємо ACK
            }
        }
    }
}
