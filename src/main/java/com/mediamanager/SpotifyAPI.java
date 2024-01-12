package com.mediamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class SpotifyAPI {

    private static final String CLIENT_ID = "2cb6629f5fc84bc6857da01ae3de1672";
    private static final String CLIENT_SECRET = "33b3e67da7a34c3bbb408c606c900441";

    public static String getAccessToken() {
        try {
            // Create the authorization header with base64 encoding
            String authHeader = "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));

            // Set up the URL and connection
            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", authHeader);

            // Set other request properties
            connection.setDoOutput(true);

            // Build the request body
            String requestBody = "grant_type=client_credentials";
            
            // Write the request body to the connection
            connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

            // Get the response from the server
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and parse the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Extract the access token from the JSON response
                String jsonResponse = response.toString();
                // Parse the JSON response and retrieve the access token
                // You need to adapt this part based on the actual JSON structure of the response
                // For simplicity, let's assume the JSON response looks like {"access_token": "your_token"}
                return jsonResponse.split("\"access_token\":\"")[1].split("\"")[0];
            } else {
                // Handle error responses
                System.out.println("Error response code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) {
        // Example usage
        String accessToken = getAccessToken();
        System.out.println("Access Token: " + accessToken);
    }
}
