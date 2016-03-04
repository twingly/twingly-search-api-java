package com.twingly.search;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

/**
 * API-wide constants
 */
public final class Constants {

    /**
     * The constant DATE_FORMAT.
     */
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * The constant TWINGLY_API_KEY_PROPERTY
     */
    public static final String TWINGLY_API_KEY_PROPERTY = "TWINGLY_SEARCH_KEY";
    private static final Properties VERSION_PROPERTIES = new Properties();
    /**
     * The constant VERSION.
     */
    public static final String VERSION = VERSION_PROPERTIES.getProperty("version");

    static {
        URL resource = Constants.class.getClassLoader().getResource("version.properties");
        assert resource != null;
        try (FileInputStream fis = new FileInputStream(resource.getFile())) {
            VERSION_PROPERTIES.load(fis);
        } catch (Exception e) {
            throw new RuntimeException("version.properties was not found or cannot be read", e);
        }
    }
}
