// YourController.java
package com.mediamanager.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import com.mediamanager.Genre;

public class YourController {

	private Stage settingsStage;
	private Stage infoStage;

	@FXML
	private ImageView settingsImg, bookImage, movieImage;

	@FXML
	private Text genreTitle, songName, songArtist, bookName, bookAuthor, movieName, movieID;

	@FXML
	private Button readMoreBtn;

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
		if (infoStage == null || !infoStage.isShowing()) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Info.fxml"));
				Parent root = loader.load();
				infoStage = new Stage();
				infoStage.setScene(new Scene(root));
				infoStage.setTitle("Info");
				infoStage.show();

			} catch (IOException err) {
				err.printStackTrace();
			}
		}
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
