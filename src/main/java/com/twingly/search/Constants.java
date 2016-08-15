package com.twingly.search;

import java.io.BufferedInputStream;
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
    /**
     * The constant VERSION.
     */
    public static final String VERSION;
    private static final Properties VERSION_PROPERTIES = new Properties();

    static {
        URL resource = Constants.class.getClassLoader().getResource("version.properties");
        assert resource != null;
        try (BufferedInputStream bis = new BufferedInputStream(resource.openStream())) {
            VERSION_PROPERTIES.load(bis);
            VERSION = VERSION_PROPERTIES.getProperty("version");
        } catch (Exception e) {
            throw new RuntimeException("version.properties was not found or cannot be read", e);
        }
    }
}
