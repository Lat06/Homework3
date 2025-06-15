package org.example;

import org.example.tcp.StoreClientTCP;
import org.example.tcp.StoreServerTCP;
import org.example.udp.StoreClientUDP;
import org.example.udp.StoreServerUDP;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Specify mode: tcp-server | tcp-client | udp-server | udp-client");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "tcp-server":
                runTCPServer();
                break;
            case "tcp-client":
                runTCPClient();
                break;
            case "udp-server":
                runUDPServer();
                break;
            case "udp-client":
                runUDPClient();
                break;
            default:
                System.out.println("Unknown mode: " + args[0]);
        }
    }

    private static void runTCPServer() {
        try {
            StoreServerTCP.main(null);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());        }
    }

    private static void runTCPClient() {
        try {
            StoreClientTCP.main(null);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());        }
    }

    private static void runUDPServer() {
        try {
            StoreServerUDP.main(null);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());        }
    }

    private static void runUDPClient() {
        try {
            StoreClientUDP.main(null);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());        }
    }
}
