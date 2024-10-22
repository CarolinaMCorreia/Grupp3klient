package org.campusmolndal;

public class SessionManager {
    private static String token;

    public static void setToken(String token) {
        SessionManager.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static void clearToken() {
        token = null;
    }
}
