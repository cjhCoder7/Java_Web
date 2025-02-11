import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class.getName());
        logger.setUseParentHandlers(false);
        try {
            FileHandler fileHandler = new FileHandler("test.log", true);
            // 设置日志格式为普通，而不是XML格式
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.info("Hello, World!");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Logger logger = Logger.getLogger("MyLogger");
        // Logger parentLogger = logger.getParent();
        // System.out.println(Arrays.toString(parentLogger.getHandlers()));
    }
}