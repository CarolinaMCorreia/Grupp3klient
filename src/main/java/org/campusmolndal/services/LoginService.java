package org.campusmolndal.services;

import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.models.User;

import org.json.JSONObject;

public class LoginService {

    public static ApiResponse login(String username, String password) {
        String url = "/auth/login";
        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        ApiResponse apiResponse = ApiConnection.sendRequest(url, "POST", jsonInputString);
        if (apiResponse.isSuccessful()) {
            User.name = username;
            JSONObject jsonObject = new JSONObject(apiResponse.getBody());
            User.jwt = jsonObject.getString("token");
        }
        return apiResponse;
    }

    public static ApiResponse getUserName(String username) {
        String url = String.format("/admin/user/%s", username);
        return ApiConnection.sendRequest(url, "GET", null);
    }

    public static ApiResponse deleteUser(String username){
        String url = String.format("/admin/user/%s", username);
        return ApiConnection.sendRequest(url, "DELETE", null);
    }

    public static ApiResponse getAllUsers() {
        String url = "/admin/user";
        ApiResponse response = ApiConnection.sendRequest(url, "GET", null);

        // lista

        return response;

    }

    public static ApiResponse updatePassword(String username, String currentPassword, String newPassword) {
        String url = "/admin/user";
        String jsonInputString = String.format(
                "{\"username\": \"%s\", \"currentPassword\": \"%s\", \"newPassword\": \"%s\"}",
                username, currentPassword, newPassword
        );
        return ApiConnection.sendRequest(url, "PUT", jsonInputString);
    }
}
