package org.example.scaner;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

    /**
     * Host value that user inputs in the console.
     */
    private final String host;

    /**
     * Object of File class whose path user inputs in the console.
     */
    private final File file;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Input a host address: ");
        String host = scanner.nextLine();

        System.out.print("Input a file path: ");
        String path = scanner.nextLine();

        new PortScanner(host, path).scan();
    }

    /**
     * {@link PortScanner} constructor.
     *
     * @param host host address
     */
    public PortScanner(String host, String path) {
        this.host = host;
        this.file = new File(path);
    }

    /**
     * Scanning all ports on the selected host by multithreading.
     */
    public void scan() {
        List<String> logs = new ArrayList<>();
        var executorService = Executors.newFixedThreadPool(THREADS);

        System.out.println(ConsoleParams.START_LINE);
        logs.add(ConsoleParams.START_LINE.getCode());

        for (int port = MIN_PORT_NUMBER; port <= MAX_PORT_NUMBER; port++) {
            final int currentPort = port;
            executorService.execute(() -> {
                var inetSocketAddress = new InetSocketAddress(host, currentPort);

                try (var socket = new Socket()) {
                    socket.connect(inetSocketAddress, TIMEOUT);
                    var message = "[SUCCESS] Host " + host + ", port " + currentPort + " is opened";
                    System.out.println(ConsoleParams.GREEN_LIGHT + message + ConsoleParams.WHITE_LIGHT);
                    logs.add(message);
                } catch (IOException ignored) {
                }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(ConsoleParams.END_LINE);
        logs.add(ConsoleParams.END_LINE.getCode());
        writeToFile(file, logs);
    }

    /**
     * Write logs to file.
     *
     * @param file object of File class
     * @param logs list of logs
     */
    private void writeToFile(File file, List<String> logs) {
        try {
            Files.write(file.toPath(), logs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
