import org.example.scaner.PortScanner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestPortScanner {

    public static PortScanner portScanner;

    @BeforeAll
    public static void init() {
        portScanner = new PortScanner("");
    }

    @Test
    public void testScan() {

    }

    private void checkFileInDirectory(String directory) {
        assertTrue(true);
    }
}
