import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class PropertiesTest {
    public static void main(String[] args) throws SecurityException, FileNotFoundException, IOException {
        Properties properties = System.getProperties();
        System.out.println(properties.get("os.name"));
        System.out.println(properties.get("os.version"));
        System.out.println(properties.get("os.arch"));
        // properties.list(System.out);

        LogManager logManager = LogManager.getLogManager();
        logManager.readConfiguration(new FileInputStream("test.properties"));
        Logger logger = Logger.getLogger("test");
        logger.config("Hello, World!");
    }
}
