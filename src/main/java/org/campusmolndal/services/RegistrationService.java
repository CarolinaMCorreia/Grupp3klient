package org.campusmolndal.services;

import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;

public class RegistrationService {

    public static ApiResponse register(String username, String password) {
        String url = "/auth/register";
        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        return ApiConnection.sendRequest(url, "POST", jsonInputString);
    }

    // Du kan lägga till fler metoder som kallar på `ApiConnection` här, som t.ex. login, update, etc.
}
