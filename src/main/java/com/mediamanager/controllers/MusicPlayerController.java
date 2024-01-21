/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mediamanager.controllers;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.scene.web.WebEngine;

public class MusicPlayerController {

    @FXML
    WebView webView;

    WebEngine webEngine;

    public void initialize() {
        webEngine = webView.getEngine();
        webEngine.load("https://open.spotify.com/embed/track/4l62h4tiuUwn7eD6hxMlVQ");
    }
}
