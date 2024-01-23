package com.mediamanager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;


public class SongAPI {

	// private static String CLIENT_ID = "2cb6629f5fc84bc6857da01ae3de1672";
	// private static String CLIENT_SECRET = "33b3e67da7a34c3bbb408c606c900441";

	private static String ACCESS_TOKEN;

	public SongAPI() {
		ACCESS_TOKEN = getAccessToken();
	}

	private static String getAccessToken() {
		try {
			String authHeader = "Basic MmNiNjYyOWY1ZmM4NGJjNjg1N2RhMDFhZTNkZTE2NzI6MzNiM2U2N2RhN2EzNGMzYmJiNDA4YzYwNmM5MDA0NDE=";

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

	public static void refreshAccessToken() {
		if (checkAccessToken(ACCESS_TOKEN) != 1) {
			ACCESS_TOKEN = getAccessToken();
		}
	}

	public static String[] getGenres() {
		try {
			refreshAccessToken();
			URL url = new URL("https://api.spotify.com/v1/recommendations/available-genre-seeds");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}

				reader.close();

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
				System.out.println("Error response code: " + responseCode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String[] getSongByGenre(String genre) {
		try {
			refreshAccessToken();
			String[] random_wildcards = { "%25a%25", "a%25", "%25a", "%25e%25", "e%25", "%25e", "%25i%25", "i%25",
					"%25i", "%25o%25", "o%25", "%25o", "%25u%25", "u%25", "%25u" };
			String apiUrl = "https://api.spotify.com/v1/search?q=" + getRandomString(random_wildcards) + "%20genre%3A"
					+ genre.replace(" ", "%20")
					+ "&type=track&limit=1&offset=" + getRandomNumber(0, 200);
			URL url = new URL(apiUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("GET");

			connection.setRequestProperty("Authorization", "Bearer " + ACCESS_TOKEN);

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder response = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				JSONObject jsonResponse = new JSONObject(response.toString());

				JSONArray items = jsonResponse.getJSONObject("tracks").getJSONArray("items");
				if (items.length() > 0) {
					JSONObject track = items.getJSONObject(0);

					String trackId = track.getString("id");
					String songName = track.getString("name");

					JSONArray artists = track.getJSONArray("artists");
					if (artists.length() > 0) {
						JSONObject artist = artists.getJSONObject(0);
						String artistName = artist.getString("name");

						return new String[] { songName, artistName, trackId };
					}
				}
			} else {
				System.out.println("Error: " + responseCode);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getRandomString(String[] array) {
		if (array != null && array.length > 0) {
			Random random = new Random();
			int randomIndex = random.nextInt(array.length);
			return array[randomIndex];
		} else {
			return null;
		}
	}

	private static int getRandomNumber(int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("Max must be greater than min");
		}

		Random random = new Random();
		return random.nextInt((max - min) + 1) + min;
	}
}