package org.campusmolndal.controllers;

import java.io.IOException;

import org.campusmolndal.App;
import org.campusmolndal.models.City;
import org.campusmolndal.models.Country;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

public class CityController {
    @FXML
    Label cityNameLabel;

    @FXML
    Text populationText;

    @FXML
    Text knownForText;

    Country country;

    public void initialize(City city, Country country) {
        this.country = country;

        cityNameLabel.setText(city.getName());
        populationText.setText(String.valueOf(city.getPopulation())); // Convert Long to String
        knownForText.setText(city.getKnownFor());
    }

    @FXML
    private void returnToCountry() throws IOException {
        App.setRoot("country");
        CountryController countryController = App.loadController("country");
        countryController.initialize(country);
    }
}
