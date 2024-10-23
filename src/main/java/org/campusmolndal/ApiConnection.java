package org.campusmolndal;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.campusmolndal.models.User;

public class ApiConnection {
    static final String BASE_URL = "http://localhost:5000"; 
    
    public static ApiResponse sendRequest(String urlString, String method, String jsonInput) {
        try {
            URL url = new URL(BASE_URL + urlString);
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

    public static ApiResponse sendAuthorizedRequest(String urlString, String method, String jsonInput) {
        try {
            URL url = new URL(BASE_URL + urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.addRequestProperty("Authorization", "Bearer " + User.jwt);
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

    public static ApiResponse sendPublicRequest(String url, String post, String jsonInputString) {
        return null;
    }
    public static ApiResponse sendGetRequest(String urlString, String method, String jsonInput) {
        if (urlString == null || urlString.isEmpty()) {
            return new ApiResponse(HttpURLConnection.HTTP_BAD_REQUEST, "URL cannot be null or empty");
        }

        HttpURLConnection connection = null;
        BufferedReader in = null;
        try {
            URL url = new URL(BASE_URL + urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Authorization", "Bearer " + User.jwt);
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);

            // Sending JSON input if provided
            if (jsonInput != null && !jsonInput.isEmpty()) {
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // Read response
            InputStream stream = (responseCode >= 200 && responseCode < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            // Check if the stream is null
            if (stream != null) {
                in = new BufferedReader(new InputStreamReader(stream));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return new ApiResponse(responseCode, response.toString());
            } else {
                return new ApiResponse(responseCode, "No response received");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, "An error occurred: " + e.getMessage());
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }


}
