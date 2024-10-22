package org.campusmolndal.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class City {
    private String name;     // Name of the city
    private Long population;  // Population of the city
    private String knownFor;  // What the city is known for

    public City() {}

    public City(String name, Long population, String knownFor) {
        this.name = name;
        this.population = population;
        this.knownFor = knownFor;
    }

    public static List<City> getList(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, new TypeReference<List<City>>() {});
    }

    public String getName() {
       return name;
    }

    public Long getPopulation() {
        return population;
    }

    public String getKnownFor() {
       return knownFor;
    }
    
}
