// YourController.java
package com.mediamanager.controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class YourController {
	
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
}
