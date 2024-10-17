package org.campusmolndal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiConnection {

    public static ApiResponse sendRequest(String urlString, String method, String jsonInput) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            if (jsonInput != null && !jsonInput.isEmpty()) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
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