package com.mediamanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class BookAPI {

    public static String[] getBookBySubject(String subject) {
        try {
            // Construct the updated URL with maxResults=10 and langRestrict=en
            String apiUrl = "https://www.googleapis.com/books/v1/volumes?q=" + subject.replace(" ", "%20")
                    + "&maxResults=10&langRestrict=en";
            URL url = new URL(apiUrl);

            // Open the connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Check the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read the response body
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                JSONArray items = jsonResponse.getJSONArray("items");

                if (items.length() > 0) {
                    // Randomly select a book from the list
                    Random random = new Random();
                    int randomIndex = random.nextInt(items.length());

                    JSONObject volumeInfo = items.getJSONObject(randomIndex).getJSONObject("volumeInfo");

                    // Extract book information
                    String bookName = volumeInfo.getString("title");
                    String author = volumeInfo.getJSONArray("authors").getString(0);

                    // Check if "imageLinks" exists before accessing it
                    String smallThumbnail;
                    if (volumeInfo.has("imageLinks")) {
                        smallThumbnail = volumeInfo.getJSONObject("imageLinks").getString("smallThumbnail");
                    } else {
                        // Set to the relative path of a default image in resources
                        smallThumbnail = "path/to/resources/book_default.png";
                    }

                    String canonicalVolumeLink = volumeInfo.getString("canonicalVolumeLink");

                    // Return the information as an array of strings
                    return new String[]{bookName, author, smallThumbnail, canonicalVolumeLink};
                }
            } else {
                System.out.println("Error response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return null if there was an issue retrieving book information
        return null;
    }
}
