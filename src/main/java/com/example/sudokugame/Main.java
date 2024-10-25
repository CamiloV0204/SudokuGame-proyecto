package com.example.sudokugame;

import com.example.sudokugame.view.WelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main class of the Sudoku game, responsible for launching the application.
 * Extends JavaFX's Application class to handle the graphical interface lifecycle.
 */
public class Main extends Application {

    /**
     * The main method that launches the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    /**
     * Initializes and shows the welcome screen of the application.
     *
     * @param primaryStage The main stage of the JavaFX application.
     * @throws IOException If there is an error loading the welcome screen.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        WelcomeStage.getInstance(); // Show the welcome screen
    }
}
