package org.campusmolndal.models;

import java.util.List;

public class User {
    public static String name = "";
    public static String jwt = "";
    public static String expiration = "";
    public static boolean isAdmin = false;
    public static List<String> authorities;

    public static void resetSession() {
        User.name = null;
        User.jwt = null;
        User.isAdmin = false;
        User.expiration = null;
        User.authorities = null;
    }

    public static boolean jwtIsExpired() {
        return (Long.parseLong(expiration) < System.currentTimeMillis());
    }
}
