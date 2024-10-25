package com.example.sudokugame.controller;

import com.example.sudokugame.view.GameStage;
import com.example.sudokugame.view.WelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

/** Authors:
 Camilo Vidales Lucumi -2342505
 Hector Ivan Sanchez - 2340635
 Emails:
 camilo.vidales@correounivalle.edu.co
 hector.ivan.sanchez@correounivalle.edu.co
 */

/**
 * Controller for the welcome screen of the Sudoku game.
 * Manages the transition from the welcome screen to the game screen when the start button is pressed.
 */
public class WelcomeController {

    /**
     * Handles the action triggered by pressing the start button.
     * Initializes the game controller and switches to the game screen.
     *
     * @param event The event triggered when the start button is pressed.
     * @throws IOException if there is an error loading the game screen.
     */
    @FXML
    void buttonStart(ActionEvent event) throws IOException {
        GameStage.getInstance().getGameController().initialize(); // Initialize the game controller
        WelcomeStage.deleteInstance(); // Close the welcome stage
    }
}
