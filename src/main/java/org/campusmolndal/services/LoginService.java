package org.campusmolndal.services;

import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;

public class LoginService {

        public static ApiResponse login(String username, String password) {
            String url = "http://localhost:8080/auth/login";
            String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

            return ApiConnection.sendRequest(url, "POST", jsonInputString);
        }

}
