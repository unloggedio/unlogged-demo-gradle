package org.unlogged.demo.gradle.utils;

public class LocationUtils {
    public static String getLocationFromAddress(String address) {
        String[] parts = address.split(" ");
        return parts[parts.length - 1];
    }
}
