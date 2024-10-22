package org.campusmolndal.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Country {
    private Long id;               // Lägg till ett ID-fält för landet
    private String name;           // Landets namn
    private String language;       // Språket som talas i landet
    private String population;       // Antal invånare
    private Continent continent;   // Koppling till världsdel

    public Country() {
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getPopulation() {
        return population;
    }

    public Continent getContinent() {
        return continent;
    }

    public static List<Country> getList(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, new TypeReference<List<Country>>() {});
    }
    
}
