package org.campusmolndal.controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.App;
import org.campusmolndal.services.LoginService;

public class LoginController {

    @FXML
    private TextField fUsername;
    @FXML
    private PasswordField fPassword;
    @FXML
    private Label fErrorMessage;

    @FXML
    private void switchToRegister() throws IOException {
        App.setRoot("register");
    }

    @FXML
    private void login() throws IOException {
        String username = fUsername.getText();
        String password = fPassword.getText();
        if (username.isBlank() || password.isBlank()) {
            fErrorMessage.setText("Enter valid username and password");
        } else {
            ApiResponse response = LoginService.login(username, password);
            if (response.isSuccessful()) {
                fErrorMessage.setText("Login successful!"); // Hantera framgång

                // Kontrollera om användaren är admin
                if (username.equalsIgnoreCase("admin")) {
                    // Om admin, ladda adminhomepage
                    App.setRoot("adminhomepage");
                    AdminHomePageController adminHomePageController = App.loadController("adminhomepage");
                    adminHomePageController.setUsername(username);
                } else {
                    // Om vanlig användare, ladda homepage
                    App.setRoot("homepage");
                    HomePageController homePageController = App.loadController("homepage");
                    homePageController.setUsername(username);
                }


            } else {
                fErrorMessage.setText("Login failed: " + response.getBody()); // Hantera fel
            }
        }
    }
}
