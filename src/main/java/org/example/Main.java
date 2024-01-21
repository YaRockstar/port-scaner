package org.example;

import org.example.scaner.PortScanner;

public class Main {

    private static final String TEST_HOST = "old-orel-city.narod.ru";

    public static void main(String[] args) {
        var portScanner = new PortScanner(TEST_HOST);
        portScanner.parallelScan();
    }
}