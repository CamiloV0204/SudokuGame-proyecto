package com.example.sudokugame.controller;

import com.example.sudokugame.model.Sudoku;
import com.example.sudokugame.view.GameStage;
import com.example.sudokugame.view.WelcomeStage;
import com.example.sudokugame.view.alert.AlertBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

/** Authors:
 Camilo Vidales Lucumi -2342505
 Hector Ivan Sanchez - 2340635
 Emails:
 camilo.vidales@correounivalle.edu.co
 hector.ivan.sanchez@correounivalle.edu.co
 */

/**
 * Controller for managing the Sudoku game actions, including input validation,
 * suggestion generation, and navigation between screens.
 */
public class GameController {

    @FXML
    private GridPane gridPane; // The grid container for the Sudoku board
    Sudoku sudoku = new Sudoku(); // The Sudoku game model
    private int[][] sudokuPlayer = new int[6][6]; // The current state of the player's input
    private boolean[][] isSuggested = new boolean[6][6]; // Tracks if a cell was suggested

    /**
     * Initializes the game controller by setting up the Sudoku grid, and styles,
     * and assigning event handlers for cell validation.
     */
    public void initialize() {
        gridPane.getChildren().clear(); // Clear the grid before adding new cells

        // Configure columns and rows to fill the available space
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < 6; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 6); // Ensure each column takes 1/6 of the width
            gridPane.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / 6); // Ensure each row takes 1/6 of the height
            gridPane.getRowConstraints().add(rowConst);
        }

        // Generate the 6x6 grid
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txt = new TextField();
                textFieldStyle(txt); // Apply proper styling
                txt.setText(String.valueOf(sudoku.getSudoku()[i][j]));
                verifyEmptyNumber(txt, sudoku.getSudoku()[i][j]);
                gridPane.add(txt, j, i);
                onKeyTextField(txt, i, j); // Configure validation for each cell
            }
        }
    }

    /**
     * Configures real-time validation for each TextField in the grid, ensuring
     * the input adheres to Sudoku rules and constraints.
     *
     * @param txt  The TextField to validate.
     * @param row  The row position in the grid.
     * @param col  The column position in the grid.
     */
    private void onKeyTextField(TextField txt, int row, int col) {
        txt.setOnKeyReleased(event -> {
            try {
                String input = txt.getText();
                if (input.isEmpty()) {
                    return; // Do nothing if no input
                }

                int number = Integer.parseInt(input);

                // Ensure the number is between 1 and 6
                if (number < 1 || number > 6) {
                    new AlertBox().showMessage("Alerta", "Error", "Solo se permiten números entre 1 y 6.");
                    txt.clear(); // Clear the field if number is out of range
                    return;
                }

                // Validate the number against Sudoku rules
                if (!isValidSuggestion(row, col, number)) {
                    new AlertBox().showMessage("Alerta", "Error", "Número no permitido: ya existe en la fila, columna o bloque 2x3.");
                    txt.clear(); // Clear the field if the number violates rules
                } else {
                    sudokuPlayer[row][col] = number; // Store the valid number
                }

            } catch (NumberFormatException e) {
                new AlertBox().showMessage("Alerta", "Error", "Solo puedes ingresar números.");
                txt.clear(); // Clear the field if input is invalid
            }
        });
    }

    /**
     * Provides a hint for the user by suggesting a valid number for an empty cell.
     * The suggestion follows Sudoku rules.
     *
     * @param event The event triggered when the help button is clicked.
     */
    @FXML
    public void onHandleButtonHelp(ActionEvent event) {
        // Loop through each empty cell and provide a valid suggestion
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (sudokuPlayer[i][j] == 0 && sudoku.getSudoku()[i][j] == 0 && !isSuggested[i][j]) {
                    int validNumber = findValidSuggestion(i, j);
                    if (validNumber != -1) {
                        TextField txt = getTextFieldAtPosition(i, j);
                        if (txt != null) {
                            txt.setText(String.valueOf(validNumber));
                            txt.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null))); // Highlight suggestion
                            isSuggested[i][j] = true;
                        }
                        return; // Stop after one suggestion
                    }
                }
            }
        }

        new AlertBox().showMessage("Ayuda", "Sugerencia no disponible", "No se encontró ninguna sugerencia válida.");
    }

    /**
     * Finds a valid suggestion for a given empty cell that follows Sudoku rules.
     *
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @return A valid number for the cell or -1 if no valid number is found.
     */
    private int findValidSuggestion(int row, int col) {
        for (int number = 1; number <= 6; number++) {
            if (isValidSuggestion(row, col, number)) {
                return number;
            }
        }
        return -1; // No valid number found
    }

    /**
     * Validates if a suggested number can be placed in a cell, ensuring no duplication
     * in the row, column, or 2x3 block.
     *
     * @param row    The row of the cell.
     * @param col    The column of the cell.
     * @param number The number to validate.
     * @return true if the number is valid, false if it breaks Sudoku rules.
     */
    private boolean isValidSuggestion(int row, int col, int number) {
        // Validate the row
        for (int c = 0; c < 6; c++) {
            if (sudokuPlayer[row][c] == number || sudoku.getSudoku()[row][c] == number) {
                return false;
            }
        }

        // Validate the column
        for (int r = 0; r < 6; r++) {
            if (sudokuPlayer[r][col] == number || sudoku.getSudoku()[r][col] == number) {
                return false;
            }
        }

        // Validate the 2x3 block
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (sudokuPlayer[r][c] == number || sudoku.getSudoku()[r][c] == number) {
                    return false;
                }
            }
        }
        return true; // The number is valid
    }

    /**
     * Retrieves the TextField at a specific position in the grid.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The TextField at the given position.
     */
    private TextField getTextFieldAtPosition(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;
            }
        }
        return null;
    }

    /**
     * Handles the confirm button click, showing a message that confirms the game state.
     *
     * @param event The event triggered by the confirm button.
     */
    @FXML
    public void onHandleButtonConfirm(ActionEvent event) {
        new AlertBox().showMessageInformation("Confirmación", "Estado del Juego", "Botón Confirmar presionado.");
    }

    /**
     * Returns the player to the welcome screen when the back button is clicked.
     *
     * @param actionEvent The event triggered by the back button.
     * @throws IOException if an I/O error occurs.
     */
    @FXML
    public void onHandleButtonBackToPlay(ActionEvent actionEvent) throws IOException {
        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }

    /**
     * Verifies whether the cell is predefined or empty, applying appropriate styling.
     *
     * @param txt The TextField to style.
     * @param n   The number in the cell (0 if empty).
     */
    private void verifyEmptyNumber(TextField txt, int n) {
        if (n != 0) {
            txt.setEditable(false);
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240), null, null)));
        } else {
            txt.clear();
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        }
    }

    /**
     * Applies custom styling to each TextField in the Sudoku grid.
     *
     * @param txt The TextField to style.
     * @return The styled TextField.
     */
    public TextField textFieldStyle(TextField txt) {
        txt.setMaxWidth(70);  // Set width
        txt.setMaxHeight(70);  // Set height
        txt.setFont(new Font("Verdana", 20)); // Set font and size
        txt.setAlignment(Pos.CENTER); // Center-align the text
        return txt;
    }
}
