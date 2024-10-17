package org.campusmolndal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    public static ApiResponse register(String username, String password) {
        try {
            URL url = new URL("http://localhost:8080/auth/register");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Skapa JSON-data
            String jsonInputString = String.format("{\"username\": \"%s\", \"password\": \"%s\"}", username, password);

            // Skicka JSON-data i request body
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Läs svaret
            BufferedReader in;
            if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
            }

            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new ApiResponse(connection.getResponseCode(), response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, "An error occurred: " + e.getMessage());
        }
    }
}
