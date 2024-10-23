package org.campusmolndal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.models.User;

import org.json.JSONObject;

import java.util.Set;

import static org.campusmolndal.ApiConnection.sendRequest;

public class LoginService {

    public static ApiResponse login(String username, String password) {
        String url = "/auth/login";
        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        ApiResponse apiResponse = sendRequest(url, "POST", jsonInputString);
        if (apiResponse.isSuccessful()) {
            User.name = username;
            JSONObject jsonObject = new JSONObject(apiResponse.getBody());
            User.jwt = jsonObject.getString("token");
        }
        return apiResponse;
    }

    public static ApiResponse getUserName(String username) {
        String url = String.format("/admin/user/%s", username);
        return ApiConnection.sendGetRequest(url, "GET", null);
    }

    public static ApiResponse deleteUser(String username){
        String url = String.format("/admin/user/%s", username);
        return sendRequest(url, "DELETE", null);
    }

    public static ApiResponse getAllUsers() {
        String url = "/admin/user";
        ApiResponse response = ApiConnection.sendGetRequest(url, "GET", null);
        return response;
    }


    public static ApiResponse updatePassword(String username, String currentPassword, String newPassword) {
        String url = "/admin/user";
        String jsonInputString = String.format(
                "{\"username\": \"%s\", \"currentPassword\": \"%s\", \"newPassword\": \"%s\"}",
                username, currentPassword, newPassword
        );
        return sendRequest(url, "PUT", jsonInputString);
    }

}
