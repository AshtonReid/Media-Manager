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
            String authHeader = "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));

            URL url = new URL("https://accounts.spotify.com/api/token");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Authorization", authHeader);

            connection.setDoOutput(true);

            String requestBody = "grant_type=client_credentials";

            connection.getOutputStream().write(requestBody.getBytes(StandardCharsets.UTF_8));

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String jsonResponse = response.toString();
                return jsonResponse.split("\"access_token\":\"")[1].split("\"")[0];
            } else {
                System.out.println("Error response code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int checkAccessToken(String accessToken) {
        try {
            // Set up the URL and connection
            URL url = new URL("https://api.spotify.com/v1/recommendations/available-genre-seeds");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Get the response from the server
            int responseCode = connection.getResponseCode();

            // Check if the token is valid based on the response code
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Token is valid
                return 1;
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                // Token is invalid or expired
                return 0;
            } else {
                // Handle other error responses
                System.out.println("Error response code: " + responseCode);
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        String accessToken = getAccessToken();
        System.out.println("Access Token: " + accessToken);
        System.out.println(checkAccessToken(accessToken));
    }
}
