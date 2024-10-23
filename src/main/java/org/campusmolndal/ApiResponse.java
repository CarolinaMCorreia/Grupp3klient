package org.campusmolndal;

import org.campusmolndal.models.UserDto;

import java.util.List;

public class ApiResponse {
    private int statusCode;
    private String body;
    private List<UserDto> users;

    public ApiResponse(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }

    public List<UserDto> getUsers() {
        return users;
    }
    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
