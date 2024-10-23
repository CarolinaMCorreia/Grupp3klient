package org.campusmolndal.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.models.UserDto;
import org.campusmolndal.models.UserDtoAdapter;
import org.campusmolndal.services.LoginService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;


public class UserController {

    @FXML
    private TextField fUsername;
    @FXML
    private PasswordField fPassword;
    @FXML
    private Label fErrorMessage;
    @FXML
    private VBox userOptions;
    @FXML
    private PasswordField fNewPassword;
    @FXML
    private PasswordField fCurrnetPassword;
    @FXML
    private ListView<String> userList;
    @FXML
    private Label foundUsernameLabel;
    @FXML
    private TableView<UserDto> userTableView; // Bind the TableView
    @FXML
    private TableColumn<UserDto, Integer> idCol; // Bind ID column
    @FXML
    private TableColumn<UserDto, String> usernameCol; // Bind username column
    @FXML
    private TableColumn<UserDto, String> authoritiesCol; // Bind authorities column
    private Gson gson = new Gson();

    private ObservableList<UserDto> userObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        gson = new GsonBuilder()
                .registerTypeAdapter(UserDto.class, new UserDtoAdapter())
                .create();
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        authoritiesCol.setCellValueFactory(new PropertyValueFactory<>("authorities"));
        userTableView.setItems(userObservableList); // Bind the observable list to the TableView
    }
    @FXML
    private void getAllUsers() {
        try {
            ApiResponse response = LoginService.getAllUsers();
            if (response.isSuccessful()) {
                String jsonBody = response.getBody();
                System.out.println("Received JSON: " + jsonBody);
                List<UserDto> users = parseUserListJson(jsonBody);
                userObservableList.setAll(users); // Use observable list for updates
                fErrorMessage.setText("Users retrieved successfully");
            } else {
                fErrorMessage.setText("Could not retrieve users");
            }
        } catch (Exception e) {
            fErrorMessage.setText("Error retrieving users: " + e.getMessage());
            e.printStackTrace(); // Print the full stack trace for debugging
        }
    }



    @FXML
    private void updateUserPassword() {
        String username = fUsername.getText();
        String newPassword = fNewPassword.getText();
        String currentPassword = fPassword.getText();

        if (username.isBlank() || newPassword.isBlank()) {
            fErrorMessage.setText("Enter both username and new password");
            return;
        }
        ApiResponse response = LoginService.updatePassword(username, currentPassword, newPassword);
        if (response.isSuccessful()) {
            fErrorMessage.setText("Password updated successfully");
        } else {
            fErrorMessage.setText("Could not update password");
        }
    }

    @FXML
    private void deleteUser() {
        String username = fUsername.getText();
        if (username.isBlank()) {
            fErrorMessage.setText("Enter username to delete");
            return;
        }
        ApiResponse response = LoginService.deleteUser(username);
        if (response.isSuccessful()) {
            fErrorMessage.setText("User deleted successfully");
            userOptions.setVisible(false);
        } else {
            fErrorMessage.setText("Could not delete user");
        }
    }

    @FXML
    private void getUserByUsername() {
        String username = fUsername.getText();

        if (username.isBlank()) {
            fErrorMessage.setText("Enter username");
        } else {
            ApiResponse response = LoginService.getUserName(username);
            if (response.isSuccessful()) {
                fErrorMessage.setText("User found");
                String jsonBody = response.getBody();
                parseUserJson(jsonBody);
            } else {
                fErrorMessage.setText("Could not find user");
                foundUsernameLabel.setText("");
            }
        }
    }


    // Method to parse JSON and set the found username label
    private void parseUserJson(String jsonBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> userMap = objectMapper.readValue(jsonBody, Map.class);
            String username = (String) userMap.get("username");
            foundUsernameLabel.setText("User found: " + username);
        } catch (IOException e) {
            fErrorMessage.setText("Error parsing user data");
            e.printStackTrace();
        }
    }

    private List<UserDto> parseUserListJson(String jsonBody) {
        Type userListType = new TypeToken<List<UserDto>>() {}.getType();
        return gson.fromJson(jsonBody, userListType);
    }

}
