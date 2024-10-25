package com.example.sudokugame.controller;

import com.example.sudokugame.view.GameStage;
import com.example.sudokugame.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;


public class WelcomeController {
    @FXML
    void buttonStart(ActionEvent event) throws IOException {

        GameStage.getInstance().getGameController().initialize();

        WelcomeStage.deleteInstance();
    }
}
