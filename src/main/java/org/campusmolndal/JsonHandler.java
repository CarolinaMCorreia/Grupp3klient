package org.campusmolndal;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonHandler {
    private static ObjectMapper objectMapper;

    /**
     * Skapar en ny instans av ObjectMapper.
     *
     * @return ObjectMapper
     */
    public JsonHandler() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Konverterar ett objekt till JSON.
     *
     * @param object objektet som ska konverteras
     * @return JSON-sträng
     */
    public static String toJson(Object object) throws IOException {
        try {
            return objectMapper.writeValueAsString(object);
            // TODO: förbättra hantering av undantag
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Konverterar en JSON-sträng till ett objekt.
     *
     * @param json JSON-strängen som ska konverteras
     * @param clazz klassen som JSON-strängen ska konverteras till
     * @return objektet
     */
    public static <T> T fromJson(String json, Class<T> clazz) throws IOException {
        try {
            return objectMapper.readValue(json, clazz);
            // TODO: förbättra hantering av undantag
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
