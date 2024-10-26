package org.campusmolndal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.campusmolndal.models.UserDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * @param json  JSON-strängen som ska konverteras
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

    public static UserDto parseUserJson(String jsonBody) {
        UserDto userDto = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> userMap = objectMapper.readValue(jsonBody, Map.class);

            // Itererar igenom roller och lägger till dem till userDto

            List<String> authorities = new ArrayList<String>();

            for (Object authority : (List) userMap.get("authorities")) {
                authorities.add((String) authority);
            }
            userDto = new UserDto((int) userMap.get("id"), (String) userMap.get("username"),
                    authorities);
            // TODO: foundUsernameLabel.setText("User found: " + username);
        } catch (IOException e) {
            // TODO: fErrorMessage.setText("Error parsing user data");
            e.printStackTrace();
        }
        return userDto;
    }
}
