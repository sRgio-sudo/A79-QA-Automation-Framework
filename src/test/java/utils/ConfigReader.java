package utils;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static Properties config = new Properties();
    private static Properties local = new Properties();

    static {
        try {
            InputStream configInput = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");
            if (configInput == null) {
                throw new RuntimeException("config.properties file not found");
            }
            config.load(configInput);
            InputStream localInput = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("local.properties");
            if (localInput != null) {
                local.load(localInput);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load properties", e);
        }
    }

    public static String getProperty(String key) {
        String value = local.getProperty(key);
        if (value == null) {
            value = config.getProperty(key);
        }
        if (value == null) {
            throw new RuntimeException(key + " not found");
        }
        return value;
    }
}
