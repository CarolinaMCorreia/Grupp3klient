package org.campusmolndal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.App;
import org.campusmolndal.services.LoginService;

import java.io.IOException;


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
    private ListView<String> userList;

    @FXML
    private void updateUserPassword(){
        // Uppdatera funktionen syns endast när man sökt efter användare
        String username = fUsername.getText();
        String newPassword = fNewPassword.getText();
        String currentPassword = fPassword.getText();

        if (username.isBlank() || newPassword.isBlank()) {
            fErrorMessage.setText("Enter both username and new password");
            return;
        }

        ApiResponse response = LoginService.updatePassword(username,currentPassword, newPassword);
        if (response.isSuccessful()) {
            fErrorMessage.setText("Password updated successfully");
        } else {
            fErrorMessage.setText("Could not update password");
        }
    }

    @FXML
    private void deleteUser(){
        // Delete funktionen syns endast när man sökt efter användare
        String username = fUsername.getText();
        if (username.isBlank()){
            fErrorMessage.setText("Enter username to delete");
            return;
        }
        ApiResponse response = LoginService.deleteUser(username);
        if (response.isSuccessful()){
            fErrorMessage.setText("User deleted successfully");
            userOptions.setVisible(false);
        }else {
            fErrorMessage.setText("Could not delete user");
        }

    }
    @FXML
    private void getAllUsers() throws IOException{
        ApiResponse response = LoginService.getAllUsers();
        // Lista

    }
    @FXML
    private void getUserByUsername()throws IOException {
        String username = fUsername.getText();

        if (username.isBlank()){
            fErrorMessage.setText("Enter username");
            userOptions.setVisible(false);
        }else {
            ApiResponse response = LoginService.getUserName(username);
            if (response.isSuccessful()){
                fErrorMessage.setText("User found");
                userOptions.setVisible(true);
                App.setRoot("user");
                HomePageController homePageController = App.loadController("user");
                homePageController.getUsername(username);

            }else{
                fErrorMessage.setText("Could not find user");
                userOptions.setVisible(false);
            }
        }
    }
}
