package org.playground.helpers;

import lombok.Getter;
import lombok.Setter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class TestConfig {
    @Getter
    private static String browser;
    private static String headless;

    public static Boolean isHeadless() {
        return Objects.equals(headless, "1");
    }

    public static void initConfig(String browser, String headless) {
        TestConfig.browser = System.getProperty("browser") == null ? browser : System.getProperty("browser");
        TestConfig.headless = System.getProperty("headless") == null ? headless : System.getProperty("headless");
    }

    public static String getApplicationUrl() {
        return getProperty("application.url");
    }

    public static String getStandardUsername() {
        return System.getenv("SWAG_STANDARD_USER");
    }

    public static String getLockedUsername() {
        return System.getenv("SWAG_LOCKED_USER");
    }

    public static String getPassword() {
        return System.getenv("SWAG_PASSWORD");
    }

    private static String getProperty(String name) {
        Properties applicationProps = new Properties();
        try {
            applicationProps.load(new FileInputStream("./src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return applicationProps.getProperty(name);
    }
}
