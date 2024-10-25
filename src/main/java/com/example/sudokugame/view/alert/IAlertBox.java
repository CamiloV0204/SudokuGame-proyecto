package com.example.sudokugame.view.alert;

/**
 * Interface defining a standard method for showing alert messages in the game.
 */
public interface IAlertBox {

    /**
     * Displays an alert with a specific title, header, and content.
     *
     * @param title   The title of the alert window.
     * @param header  The header text of the alert.
     * @param content The content message of the alert.
     */
    void showMessage(String title, String header, String content);
}
