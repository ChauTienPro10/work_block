package com.work_block.main.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigReader {
    private static Properties properties;

    public ConfigReader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

}
