package com.mediamanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MovieAPI {

    private static final String API_KEY = "7c1e45be36462f84a5912bf686ff8de0";
    private static final String API_URL = "https://api.themoviedb.org/3/search/movie";
    private static final String LANGUAGE = "en-US";

    public static String[] getMovieByGenre(String genre) {
        try {
            // Construct the API URL
            String apiUrl = String.format("%s?query=%s&language=%s&api_key=%s", API_URL, genre.replace(" ", "+"), LANGUAGE, API_KEY);

            // Create HttpURLConnection
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Get the response code
            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray results = jsonResponse.getJSONArray("results");

                if (results.length() > 0) {
                    // Pick a random movie from the results
                    JSONObject randomMovie = results.getJSONObject(new Random().nextInt(results.length()));

                    String title = randomMovie.getString("title");
                    // Unfortunately, the API doesn't provide director information directly.
                    String id = String.valueOf(randomMovie.getInt("id"));

					String posterPath;

					if (randomMovie.has("poster_path") && !randomMovie.isNull("poster_path")) {
						// Check if "poster_path" exists and is not null
						posterPath = "https://image.tmdb.org/t/p/w200" + randomMovie.getString("poster_path");
					} else {
						posterPath = "None";
					}
					
                    return new String[]{title, id, posterPath};
                } else {
                    return null;
                }
            } else {
                // Handle error response
                System.err.println("Error in API request. Response Code: " + responseCode);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
