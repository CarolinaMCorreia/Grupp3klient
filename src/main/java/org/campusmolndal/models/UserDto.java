package org.campusmolndal.models;

import java.util.List;


public class UserDto {
    private final int id;
    private final String username;
    private List<String> authorities;

    public UserDto(int id, String username, List<String> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", authorities=" + String.join(", ", authorities) +
                '}';
    }
}