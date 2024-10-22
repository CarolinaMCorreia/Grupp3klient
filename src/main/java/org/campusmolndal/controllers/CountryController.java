package org.campusmolndal.controllers;

import java.io.IOException;
import java.util.List;

import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.App;
import org.campusmolndal.models.City;
import org.campusmolndal.models.Country;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class CountryController {
    @FXML
    Label countryNameLabel;

    @FXML
    Text languageText;

    @FXML
    Text populationText;

    @FXML
    VBox navBox;

    Country country;
    List<City> cities;

    public void initialize(Country country) {
        this.country = country;

        countryNameLabel.setText(country.getName());
        languageText.setText(country.getLanguage());
        populationText.setText(country.getPopulation());

        cities = getCities();
        for (City city : cities) {
            addNavButton(city);
        }
    }

    private List<City> getCities() {
        ApiResponse apiResponse = ApiConnection.sendAuthorizedRequest(
            "/user/cities",
            "GET",
            null
        );
        if (apiResponse.isSuccessful()) {
            return City.getList(apiResponse.getBody());
            // TODO: omvandla apiResponse.getBody() till List<City>
        } else {
            return List.of();
        }
    }

    private void addNavButton(City city) {
        Button navButton = new Button(city.getName());
        navButton.setOnAction(event -> {
            try {
                App.setRoot("city");
                CityController cityController = App.loadController("city");
                cityController.initialize(city, country);
            } catch (IOException e) {
                Text error = new Text("Failed to load city view");
                navBox.getChildren().add(error);
            }
        });
        navBox.getChildren().add(navButton);
    }

    @FXML
    private void returnToContinents() throws IOException {
        App.setRoot("continents");
        ContinentController continentController = App.loadController("continents");
        continentController.initialize();
    }
}
