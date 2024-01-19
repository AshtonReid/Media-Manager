// YourController.java
package com.mediamanager.controllers;

import java.io.IOException;
import java.util.Random;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.control.Button;

import com.mediamanager.SpotifyAPI;

public class YourController {

	private Stage stage;
	private Stage settingsStage;
	private Stage infoStage;

	@FXML
	private ImageView settingsImg;

	@FXML
	private Text genreTitle;

	@FXML
	private Button readMoreBtn;

	@FXML
	void settingsBtnClick(MouseEvent event) {
		if (settingsStage == null || !settingsStage.isShowing()) {
			try {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
				Parent root = loader.load();
				settingsStage = new Stage();
				settingsStage.setScene(new Scene(root));
				settingsStage.setTitle("Settings");
				settingsStage.show();
			} catch (IOException err) {
				err.printStackTrace();
			}
		}
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
	void readMoreBtnClick(MouseEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MusicPlayer.fxml"));
			Parent root = loader.load();
			this.stage.setScene(new Scene(root));
			this.stage.show();
		} catch (IOException err) {
			err.printStackTrace();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void initialize() {
		Random random = new Random();
		String[] genres = SpotifyAPI.getGenres();
		int randomIndex = random.nextInt(genres.length);

		genreTitle.setText(genres[randomIndex].toUpperCase());

		Font.loadFont(getClass().getResourceAsStream("/fonts/Knewave.ttf"), 60);
		genreTitle.setStyle("-fx-font-family: 'Knewave'; -fx-font-size: 69;");
	}
}
