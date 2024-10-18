package org.campusmolndal.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.App;
import org.campusmolndal.services.RegistrationService;

public class RegistrationController {
    @FXML
    private TextField fUsername;
    @FXML
    private PasswordField fPassword;
    @FXML
    private Label fErrorMessage;

    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    @FXML
    private void register() throws IOException {
        String username = fUsername.getText();
        String password = fPassword.getText();
        if (username.isBlank() || password.isBlank()) {
            fErrorMessage.setText("Enter valid username and password");
        } else {
            ApiResponse response = RegistrationService.register(username, password);
            if (response.isSuccessful()) {
                fErrorMessage.setText("Registration successful!"); // Hantera framgång
            } else {
                fErrorMessage.setText("Registration failed: " + response.getBody()); // Hantera fel
            }
        }
    }


}
