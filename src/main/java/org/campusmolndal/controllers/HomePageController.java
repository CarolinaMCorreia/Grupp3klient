package org.campusmolndal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import org.campusmolndal.App;

import java.io.IOException;

public class HomePageController {

    @FXML
    private Label usernameLabel;

    // Denna metod anropas för att sätta användarnamnet på hemsidan
    public void setUsername(String username) {
        usernameLabel.setText("Inloggad som: " + username);
    }

    public void getUsername(String username){usernameLabel.setText(username);}

    // Denna metod hanterar utloggning
    @FXML
    private void logout() throws IOException {
        // Navigera tillbaka till inloggningssidan
        App.setRoot("login");
    }
}
