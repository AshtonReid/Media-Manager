// YourController.java
package com.mediamanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.text.Font;

import com.mediamanager.Genre;

public class YourController {

	@FXML
	private ImageView settingsImg, bookImage, movieImage;

	@FXML
	private Text genreTitle, songName, songArtist, bookName, bookAuthor, movieName, movieID;

	@FXML
	private WebView songEmbed;

	Genre genre = new Genre();
	String[] song = genre.getSong();
	String[] book = genre.getBook();
	String[] movie = genre.getMovie();

	int i = 0;

	@FXML
	void settingsBtnClick(MouseEvent event) {
		genre.refreshGenre();
		initialize();
	}

	@FXML
	void infoBtnClick(MouseEvent event) {
		new Alert(Alert.AlertType.INFORMATION, "Media Manager programmed by Ashton & Parker for Mr. Hasanovic.\nICS4U").showAndWait();
	}

	@FXML
	void bookBtnClicked(MouseEvent event) {
		Genre.openLink(book[3]);
	}

	@FXML
	void movieBtnClicked(MouseEvent event) {
		Genre.openLink("https://www.themoviedb.org/movie/" + movie[1]);
	}

	@FXML
	void songBtnClicked(MouseEvent event) {
		Genre.openLink("https://open.spotify.com/track/" + song[2]);
	}

	public void initialize() {
		if (i != 0) {
			genre.refreshGenre();
			song = genre.getSong();
			book = genre.getBook();
			movie = genre.getMovie();
		} else {
			i++;
		}
		Font.loadFont(getClass().getResourceAsStream("/fonts/Knewave.ttf"), 60);
		genreTitle.setStyle("-fx-font-family: 'Knewave'; -fx-font-size: 69;");
		genreTitle.setText(genre.getCurrentGenre().split(" == ")[1].toUpperCase());

		WebEngine webEngine = songEmbed.getEngine();
		webEngine.load("https://open.spotify.com/embed/track/" + song[2]);
		songName.setText(song[0]);
		songArtist.setText(song[1]);

		bookImage.setImage(new Image(book[2]));
		bookName.setText(book[0]);
		bookAuthor.setText(book[1]);

		if (movie[2] != "None") {
			movieImage.setImage(new Image(movie[2]));
		} else {
			movieImage.setImage(new Image(getClass().getResource("/images/default_movie.png").toExternalForm()));
		}
		
		movieName.setText(movie[0]);
		movieID.setText("ID: " + movie[1]);
	}
}
