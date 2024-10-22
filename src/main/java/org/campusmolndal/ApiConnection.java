package org.campusmolndal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ApiConnection {
    static final String BASE_URL = "http://localhost:5000"; 
    
    public static ApiResponse sendRequest(String urlString, String method, String jsonInput) {
        HttpURLConnection connection;
        try {
            URL url = new URI(BASE_URL + urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            // Justerar headers utifrån request-metod
            if (method.equals("POST") || method.equals("PUT")) {
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
            } else if (method.equals("GET")) {
                connection.setRequestProperty("Accept", "application/json");
            }
            connection.setDoOutput(method.equals("POST") || method.equals("PUT"));

            // Lägger till token
            String token = SessionManager.getToken();
            if (token != null && !token.isEmpty()) {
                connection.setRequestProperty("Authorization", "Bearer " + token);
            }

            // Skicka json-input om request är POST eller PUT
            if (jsonInput != null && !jsonInput.isEmpty() && (method.equals("POST") || method.equals("PUT"))) {
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

    public static ApiResponse sendPublicRequest(String urlString, String method, String jsonInput) {
        HttpURLConnection connection;
        try {
            URL url = new URI(BASE_URL + urlString).toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setDoOutput(method.equals("POST") || method.equals("PUT"));

            // Skicka json-input om request är POST eller PUT
            String jsonResponse = sendRequest(connection, jsonInput);

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

    /**
     * Skickar en request och returnerar svaret som en sträng.
     *
     * @param connection HttpURLConnection-objektet
     * @return svaret som en sträng
     * @throws IOException om ett fel inträffar
     */
    public static String sendRequest(HttpURLConnection connection, String jsonInput) throws IOException {
        if (jsonInput != null && !jsonInput.isEmpty()) {
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }
        }
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
            return readResponse(connection);
        } else {
            throw new IOException("An error occurred: " + responseCode);
        }
    }

    /**
     * Läser svaret från en HttpURLConnection och returnerar det som en sträng.
     *
     * @param connection HttpURLConnection-objektet
     * @return svaret som en sträng
     * @throws IOException om ett fel inträffar
     */
    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine.trim());
            }
            return response.toString();
        }
    }
}
