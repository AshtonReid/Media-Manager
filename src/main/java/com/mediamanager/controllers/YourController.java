// YourController.java
package com.mediamanager.controllers;

import java.io.IOException;
import javafx.animation.Interpolator;

import javafx.animation.RotateTransition;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;


public class YourController {
    
    @FXML
    private ImageView settingsImg;

    @FXML
    void settingsBtnClick(MouseEvent event) {
        try {
            
//            RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), settingsImg);
//            rotateTransition.setFromAngle(0);
//            rotateTransition.setToAngle(360);
//            
//            rotateTransition.setInterpolator(Interpolator.EASE_IN);
//            rotateTransition.play();
            
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
}
