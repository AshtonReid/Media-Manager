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

import com.mediamanager.SpotifyAPI;

public class YourController {
    
    @FXML
    private ImageView settingsImg;
    
    @FXML
    private Text genreTitle;
    
    @FXML
    void settingsBtnClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Settings");
            stage.show();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    @FXML
    void infoBtnClick(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Info.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Info");
            stage.show();
        } catch (IOException err) {
            err.printStackTrace();
        }
    }
    
    public void initialize() {
        Random random = new Random();
        String[] genres = SpotifyAPI.getGenres(SpotifyAPI.getAccessToken());
        int randomIndex = random.nextInt(genres.length);
        
        genreTitle.setText(genres[randomIndex].toUpperCase());
        
        Font.loadFont(getClass().getResourceAsStream("/fonts/Knewave.ttf"), 60);
        genreTitle.setStyle("-fx-font-family: 'Knewave'; -fx-font-size: 69;");
    }
}
