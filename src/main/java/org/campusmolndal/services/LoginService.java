package org.campusmolndal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.SessionManager;
import org.campusmolndal.models.User;

import org.json.JSONObject;

import java.util.Set;

import static org.campusmolndal.ApiConnection.*;

public class LoginService {

    public static ApiResponse login(String username, String password) {
        String url = "/auth/login";
        String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

        ApiResponse apiResponse = sendRequest(url, "POST", jsonInputString);
        if (apiResponse.isSuccessful()) {
            JSONObject jsonObject = new JSONObject(apiResponse.getBody());
            String token = jsonObject.getString("token");
            SessionManager.setToken(token);
            User.name = username;
            User.jwt = token;
        }
        return apiResponse;
    }

    public static ApiResponse getUserName(String username) {
        String url = String.format("/admin/user/%s", username);
        return ApiConnection.sendGetRequest(url, "GET", null);
    }

    public static ApiResponse deleteUser(String username) {
        String url = String.format("/admin/user/%s", username);
        return sendGetRequest(url, "DELETE", null);
    }

    public static ApiResponse getAllUsers() {
        String url = "/admin/user";
        ApiResponse response = ApiConnection.sendGetRequest(url, "GET", null);
        return response;
    }

    public static ApiResponse updateUserRoles(String username, Set<String> roles) throws JsonProcessingException {
        String url = "/admin/user/roles";

        String jsonInputString = String.format(
                "{\"username\": \"%s\", \"authorities\": %s}",
                username, new ObjectMapper().writeValueAsString(roles)
        );
        return sendGetRequest(url, "PUT", jsonInputString);
    }
}
