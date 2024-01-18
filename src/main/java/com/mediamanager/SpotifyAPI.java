package com.mediamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyAPI {

    private static String CLIENT_ID = "2cb6629f5fc84bc6857da01ae3de1672";
    private static String CLIENT_SECRET = "33b3e67da7a34c3bbb408c606c900441";

    public static String getAccessToken() {
        try {
            String authHeader = "Basic " + Base64.getEncoder()
                    .encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes(StandardCharsets.UTF_8));

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
            URL url = new URL("https://api.spotify.com/v1/recommendations/available-genre-seeds");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return 1;
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                return 0;
            } else {
                System.out.println("Error response code: " + responseCode);
                return -1;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static String[] getGenres(String accessToken) {
        try {
            // Set up the URL and connection
            URL url = new URL("https://api.spotify.com/v1/recommendations/available-genre-seeds");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            // Get the response from the server
            int responseCode = connection.getResponseCode();

            // Check if the request was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read and parse the response using a simpler regex
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response and retrieve the genres using regex
                Pattern pattern = Pattern.compile("\"([^\"]+)\",?");
                Matcher matcher = pattern.matcher(response.toString());

                List<String> genresList = new ArrayList<>();

                if (matcher.find()) {
                    while (matcher.find()) {
                        genresList.add(matcher.group(1));
                    }
                }

                String[] genres = genresList.toArray(new String[0]);

                return genres;
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
        String accessToken = getAccessToken();
        getGenres(accessToken);
    }
}
