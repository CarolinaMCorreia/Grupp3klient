package org.campusmolndal.controllers;

import java.io.IOException;
import java.util.List;

import org.campusmolndal.ApiConnection;
import org.campusmolndal.ApiResponse;
import org.campusmolndal.App;
import org.campusmolndal.models.Continent;
import org.campusmolndal.models.Country;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ContinentController {
    @FXML
    HBox continentsList;

    public void initialize() {
        setContinents(getContinents());
    }

    private List<Continent> getContinents() {
        ApiResponse apiResponse = ApiConnection.sendRequest(
            "/user/continents",
            "GET",
            null
        );
        if (apiResponse.isSuccessful()) {
            return Continent.getList(apiResponse.getBody());
            // TODO: omvandla apiresponse.getBody() till List<Continent>
        } else {
            return List.of();
        }
    }

    private void setContinents(List<Continent> continents) {
        for (Continent continent : continents) {
            addContinent(continent);
        }
    }

    private void addContinent(Continent continent) {
        VBox countriesList = new VBox();
        continentsList.getChildren().add(countriesList);

        Label continentName = new Label(continent.getName());
        countriesList.getChildren().add(continentName);

        for (Country country : getCountries()) {
            countriesList.getChildren().add(CountryButton(country));
        }
    }

    private List<Country> getCountries() {
        ApiResponse apiResponse = ApiConnection.sendRequest(
            "/user/countries",
            "GET",
            null
        );
        if (apiResponse.isSuccessful()) {
            return Country.getList(apiResponse.getBody());
        } else {
            return List.of();
        }
    }

    private Button CountryButton(Country country) {
        Button button = new Button(country.getName());
        button.setOnAction(event -> {
            try {
                App.setRoot("country");
                CountryController countryController = App.loadController("country");
                countryController.initialize(country);
            } catch (IOException e) {
                // TODO: hantera exception
            }
        });
        return button;
    }
}