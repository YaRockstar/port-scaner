import org.example.scaner.ConsoleParams;
import org.example.scaner.PortScanner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPortScanner {

    private static final String TEST_HOST = "old-orel-city.narod.ru";

    private static final String TEST_PATH = "/Users/Yaroslav/Desktop/scanner-logs.log";

    public static PortScanner portScanner;

    @BeforeAll
    public static void init() {
        portScanner = new PortScanner(TEST_HOST, TEST_PATH);
    }

    @Test
    public void testScan() {
        portScanner.scan();
        File file = new File(TEST_PATH);
        checkFileInDirectory(file);
        checkFileContents(file);
    }

    private void checkFileInDirectory(File file) {
        assertTrue(Files.exists(file.toPath()));
    }

    private void checkFileContents(File file) {
        List<String> lines = new ArrayList<>();

        try (var bufferedReader = new BufferedReader(new FileReader(file))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertTrue(lines.contains(ConsoleParams.START_LINE.getCode()));
        assertTrue(lines.contains(ConsoleParams.END_LINE.getCode()));
    }
}
