package com.mediamanager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Random;
import java.awt.Desktop;

public class Genre {

    private String currentGenre;

    public Genre() {
        initializeRandomGenre();
    }

    private void initializeRandomGenre() {
        try {
            // Load the resource using the class loader
            InputStream inputStream = getClass().getResourceAsStream("/db/GenreDB.json");

            if (inputStream != null) {
                // Read the genres from GenreDB.json
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                reader.close();

                JSONObject genreDB = new JSONObject(content.toString());
                JSONArray genresArray = genreDB.getJSONArray("genres");

                // Pick a random genre
                currentGenre = getRandomGenre(genresArray);
            } else {
                System.out.println("Resource not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getRandomGenre(JSONArray genresArray) {
        // Randomly select a genre
        Random random = new Random();
        int randomIndex = random.nextInt(genresArray.length());

        // Update the current genre
        currentGenre = genresArray.getString(randomIndex);

        return currentGenre;
    }

    public String getCurrentGenre() {
        return currentGenre;
    }

    public String[] getSong() {
        // Split the first genre by the first hyphen
        String[] genreParts = currentGenre.split(" == ");

        // Get a song based on the last hyphen
        return SongAPI.getSongByGenre(genreParts[1]);
    }

    public String[] getBook() {
        // Split the first genre by the first hyphen
        String[] genreParts = currentGenre.split(" == ");

        // Get a book based on the first hyphen
        return BookAPI.getBookBySubject(genreParts[0]);
    }

    public String[] getMovie() {
        // Split the first genre by the first hyphen
        String[] genreParts = currentGenre.split(" == ");

        // Get a book based on the first hyphen
        return MovieAPI.getMovieByGenre(genreParts[0]);
    }

    public void refreshGenre() {
        // Pick a new genre (different from the current one)
        try {
            // Load the resource using the class loader
            InputStream inputStream = getClass().getResourceAsStream("/db/GenreDB.json");

            if (inputStream != null) {
                // Read the genres from GenreDB.json
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    content.append(line);
                }

                reader.close();

                JSONObject genreDB = new JSONObject(content.toString());
                JSONArray genresArray = genreDB.getJSONArray("genres");

                String newGenre = getRandomGenre(genresArray);
                currentGenre = newGenre;
            } else {
                System.out.println("Resource not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openLink(String url) {
        try {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            } else {
                System.out.println("Desktop browsing not supported. Please open the link manually.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
