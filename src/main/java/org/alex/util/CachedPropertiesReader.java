package org.alex.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CachedPropertiesReader {
    private final String path;
    private Properties cachedProperties;


    public CachedPropertiesReader(String path) {
        this.path = path;
        cachedProperties = readProperties();
    }
    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = CachedPropertiesReader.class.getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Bad path");
            }
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Error reading the properties file: " + e.getMessage());
        }
        return properties;
    }


    public Properties getCachedProperties() {
        return new Properties(cachedProperties);
    }

}
