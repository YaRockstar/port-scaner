import org.example.scaner.PortScanner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestPortScanner {

    public static PortScanner portScanner;

    @BeforeAll
    public static void init() {
        portScanner = new PortScanner("");
    }

    @Test
    public void testScan() {

    }
}
