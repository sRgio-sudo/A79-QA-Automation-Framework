package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop = new Properties();

    static {
        try {
            InputStream input = ConfigReader
                    .class.getClassLoader()
                    .getResourceAsStream("config.properties");
            if (input == null) {
                throw new RuntimeException("config.properties file not found");
            }
            prop.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load config.properties");
        }
    }
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
}
