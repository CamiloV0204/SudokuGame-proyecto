package com.example.sudokugame.view.alert;

import javafx.scene.control.Alert;

/**
 * Class responsible for displaying alert messages in the Sudoku game.
 * Implements the IAlertBox interface to define a standard alert method.
 */
public class AlertBox implements IAlertBox {

    /**
     * Displays an error alert message.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content message of the alert.
     */
    @Override
    public void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // Show the alert and wait for user interaction
    }

    /**
     * Displays an informational alert message.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content message of the alert.
     */
    public void showMessageInformation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait(); // Show the alert and wait for user interaction
    }
}
