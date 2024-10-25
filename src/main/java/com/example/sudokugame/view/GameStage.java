package com.example.sudokugame.view;

import com.example.sudokugame.controller.GameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the main game window (stage) for the Sudoku game.
 * Follows the Singleton pattern to ensure only one instance exists.
 */
public class GameStage extends Stage {
    private GameController gameController; // The controller for the game

    /**
     * Constructor that sets up and shows the game window by loading the FXML file.
     *
     * @throws IOException If there is an error loading the FXML file.
     */
    public GameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/sudokugame.fxml"));
        Parent root = loader.load();
        gameController = loader.getController(); // Assign the game controller

        setTitle("Ventana juego de Sudoku"); // Set the window title
        Scene scene = new Scene(root);

        // Set the window icon
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/sudokugame/images/flaticon.png"))));

        setScene(scene);
        setResizable(false); // Make the window non-resizable
        show(); // Display the window
    }

    /**
     * Returns the controller associated with the game view.
     *
     * @return The game controller.
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * Returns the single instance of GameStage using the Singleton pattern.
     *
     * @return The single instance of GameStage.
     * @throws IOException If there is an error creating the instance.
     */
    public static GameStage getInstance() throws IOException {
        return GameStageHolder.INSTANCE = new GameStage();
    }

    /**
     * Inner static class to hold the singleton instance of GameStage.
     */
    private static class GameStageHolder {
        private static GameStage INSTANCE; // Singleton instance of GameStage
    }

    /**
     * Deletes the current instance of GameStage by closing the window and clearing the reference.
     */
    public static void deleteInstance() {
        GameStageHolder.INSTANCE.close(); // Close the window
        GameStageHolder.INSTANCE = null;  // Clear the instance reference
    }
}
