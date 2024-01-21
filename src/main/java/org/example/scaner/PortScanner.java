package org.example.scaner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PortScanner {

    /**
     * Numbers of threads.
     */
    private static final int THREADS = 100;

    /**
     * Timeout value (ms).
     */
    private static final int TIMEOUT = 100;

    /**
     * Minimal port number.
     */
    private static final int MIN_PORT_NUMBER = 0;

    /**
     * Maximal port number.
     */
    private static final int MAX_PORT_NUMBER = 65535;

    private final String host;

    /**
     * {@link PortScanner} constructor.
     *
     * @param host host address
     */
    public PortScanner(String host) {
        this.host = host;
    }

    /**
     * Scanning all ports on the selected host.
     */
    public void scan() {
        System.out.println("[INFO] Scanning is started");
        for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
            var inetSocketAddress = new InetSocketAddress(host, port);

            try (var socket = new Socket()) {
                socket.connect(inetSocketAddress, TIMEOUT);
                System.out.printf("\u001B[32m[SUCCESS] Host %s, port %d is opened\u001B[0m\n", host, port);
            } catch (IOException e) {
//                System.err.println("[ERROR] " + e.getMessage());
            }
        }
        System.out.println("[INFO] Scanning is finished");
    }

    /**
     * Scanning all ports on the selected host by multithreading.
     */
    public void parallelScan() {
        System.out.println("[INFO] Scanning is started");
        var executorService = Executors.newFixedThreadPool(THREADS);

        for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
            final int currentPort = port;
            executorService.execute(() -> {
                var inetSocketAddress = new InetSocketAddress(host, currentPort);

                try (var socket = new Socket()) {
                    socket.connect(inetSocketAddress, TIMEOUT);
                    System.out.printf("\u001B[32m[SUCCESS] Host %s, port %d is opened\u001B[0m\n", host, currentPort);
                } catch (IOException e) {
//                    System.err.println("[ERROR] " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("[INFO] Scanning is finished");
    }
}
