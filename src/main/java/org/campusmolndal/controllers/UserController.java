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
import org.campusmolndal.ApiResponse;
import org.campusmolndal.models.UserDto;
import org.campusmolndal.models.UserDtoAdapter;
import org.campusmolndal.services.LoginService;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class UserController {

    @FXML
    private Label fRoleUpdateMessage;
    @FXML
    private TextField fUsername;
    @FXML
    private Label fErrorMessage;
    @FXML
    private TextField fRoleUsername;
    @FXML
    private ComboBox<String> fRoleComboBox;
    @FXML
    private Label foundUsernameLabel;
    @FXML
    private TextField fUsernameToDelete;
    @FXML
    private TableView<UserDto> userTableView;
    @FXML
    private TableColumn<UserDto, Integer> idCol;
    @FXML
    private TableColumn<UserDto, String> usernameCol;
    @FXML
    private TableColumn<UserDto, String> authoritiesCol;

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
        userTableView.setItems(userObservableList);
    }

    @FXML
    private void getAllUsers() {
        try {
            ApiResponse response = LoginService.getAllUsers();
            if (response.isSuccessful()) {
                String jsonBody = response.getBody();
                System.out.println("Received JSON: " + jsonBody);
                List<UserDto> users = parseUserListJson(jsonBody);
                userObservableList.setAll(users);
                fErrorMessage.setText("Users retrieved successfully");
            } else {
                fErrorMessage.setText("Could not retrieve users");
            }
        } catch (Exception e) {
            fErrorMessage.setText("Error retrieving users: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteUser() {
        String username = fUsernameToDelete.getText();
        if (username.isBlank()) {
            fErrorMessage.setText("Enter username to delete");
            return;
        }
        ApiResponse response = LoginService.deleteUser(username);
        if (response.isSuccessful()) {
            fErrorMessage.setText("User deleted successfully");
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

    @FXML
    private void updateUserRole() {
        String username = fRoleUsername.getText();
        String selectedRole = fRoleComboBox.getValue();

        if (username.isBlank()) {
            fRoleUpdateMessage.setText("Enter username to update role");
            return;
        }

        if (selectedRole == null) {
            fRoleUpdateMessage.setText("Select a role to assign");
            return;
        }

        try {
            Set<String> rolesToUpdate = Set.of(selectedRole);
            System.out.println("Sending payload for update: " + username + " with roles: " + rolesToUpdate);

            ApiResponse response = LoginService.updateUserRoles(username, rolesToUpdate);

            if (response.isSuccessful()) {
                fRoleUpdateMessage.setText("User role updated successfully");
            } else {
                fRoleUpdateMessage.setText("Could not update user role");
            }
        } catch (JsonProcessingException e) {
            fRoleUpdateMessage.setText("Error processing JSON: " + e.getMessage());
        } catch (Exception e) {
            fRoleUpdateMessage.setText("Unexpected error: " + e.getMessage());
        }
    }

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
        Type userListType = new TypeToken<List<UserDto>>() {
        }.getType();
        return gson.fromJson(jsonBody, userListType);
    }
}
