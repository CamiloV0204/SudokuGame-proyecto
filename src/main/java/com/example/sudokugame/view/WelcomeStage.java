package com.example.sudokugame.view;

import com.example.sudokugame.controller.WelcomeController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Represents the welcome screen for the Sudoku game.
 * Follows the Singleton pattern to ensure only one instance exists.
 */
public class WelcomeStage extends Stage {
    private WelcomeController welcomeController; // The controller for the welcome screen

    /**
     * Constructor that sets up and shows the welcome screen by loading the FXML file.
     *
     * @throws IOException If there is an error loading the FXML file.
     */
    public WelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/sudokugame/welcome.fxml"));
        Parent root = loader.load();
        welcomeController = loader.getController(); // Assign the welcome controller

        setTitle("Juego Sudoku - Bienvenido"); // Set the window title
        Scene scene = new Scene(root);

        // Set the window icon
        getIcons().add(new Image(String.valueOf(getClass().getResource("/com/example/sudokugame/images/flaticon.png"))));

        setScene(scene);
        setResizable(false); // Make the window non-resizable
        show(); // Display the window
    }

    /**
     * Returns the controller associated with the welcome view.
     *
     * @return The welcome controller.
     */
    public WelcomeController getWelcomeController() {
        return welcomeController;
    }

    /**
     * Returns the single instance of WelcomeStage using the Singleton pattern.
     *
     * @return The single instance of WelcomeStage.
     * @throws IOException If there is an error creating the instance.
     */
    public static WelcomeStage getInstance() throws IOException {
        return WelcomeStageHolder.INSTANCE = new WelcomeStage();
    }

    /**
     * Inner static class to hold the singleton instance of WelcomeStage.
     */
    private static class WelcomeStageHolder {
        private static WelcomeStage INSTANCE; // Singleton instance of WelcomeStage
    }

    /**
     * Deletes the current instance of WelcomeStage by closing the window and clearing the reference.
     */
    public static void deleteInstance() {
        WelcomeStageHolder.INSTANCE.close(); // Close the window
        WelcomeStageHolder.INSTANCE = null;  // Clear the instance reference
    }
}
