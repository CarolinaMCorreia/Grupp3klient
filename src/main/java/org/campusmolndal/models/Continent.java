package org.campusmolndal.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Continent {

    private String name;


    public Continent() {
    }

    public String getName() {
        return name;
    }

    public static List<Continent> getList(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(body, new TypeReference<List<Continent>>() {});
    }

}
