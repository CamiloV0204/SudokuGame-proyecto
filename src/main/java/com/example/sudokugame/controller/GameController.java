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

public class GameController {
    @FXML
    private GridPane gridPane;
    Sudoku sudoku = new Sudoku();
    private int[][] sudokuPlayer = new int[6][6];
    private boolean[][] isSuggested = new boolean[6][6]; // Para rastrear si una celda fue sugerida

    public void initialize() {
        gridPane.getChildren().clear();  // Limpiar el GridPane antes de agregar nuevas celdas

        // Ajustar las columnas y filas para ocupar todo el espacio disponible
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();

        for (int i = 0; i < 6; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / 6); // Asegurar que cada columna ocupe 1/6 del ancho del GridPane
            gridPane.getColumnConstraints().add(colConst);

            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / 6); // Asegurar que cada fila ocupe 1/6 del alto del GridPane
            gridPane.getRowConstraints().add(rowConst);
        }

        // Generar la cuadrícula 6x6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                TextField txt = new TextField();
                textFieldStyle(txt); // Aplicar el estilo adecuado
                txt.setText(String.valueOf(sudoku.getSudoku()[i][j]));
                verifyEmptyNumber(txt, sudoku.getSudoku()[i][j]);
                gridPane.add(txt, j, i);
                onKeyTextField(txt, i, j);  // Configurar validación para cada celda
            }
        }
    }

    private void onKeyTextField(TextField txt, int row, int col) {
        txt.setOnKeyReleased(event -> {
            try {
                // Convertir la entrada a número
                String input = txt.getText();
                if (input.isEmpty()) {
                    return; // Si no hay nada ingresado, no hacemos nada
                }

                int number = Integer.parseInt(input);

                // Validar que el número esté entre 1 y 6
                if (number < 1 || number > 6) {
                    new AlertBox().showMessage("Alerta", "Error", "Solo se permiten números entre 1 y 6.");
                    txt.clear();  // Limpiar la celda si el número no está en el rango permitido
                    return;
                }

                if (!isValidSuggestion(row, col, number)) {
                    new AlertBox().showMessage("Alerta", "Error", "Número no permitido: ya existe en la fila, columna o bloque 2x3.");
                    txt.clear();  // Limpiar la celda si el número es inválido
                } else {
                    // Si el número es válido, se asigna al tablero y la interfaz
                    sudokuPlayer[row][col] = number;
                }


            } catch (NumberFormatException e) {
                new AlertBox().showMessage("Alerta", "Error", "Solo puedes ingresar números.");
                txt.clear();  // Limpiar la celda si no es un número válido
            }
        });
    }

    // Función de "Ayuda" para dar sugerencias cumpliendo las reglas del Sudoku
    @FXML
    public void onHandleButtonHelp(ActionEvent event) {
        // Recorrer cada celda vacía para sugerir un número válido
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (sudokuPlayer[i][j] == 0 && sudoku.getSudoku()[i][j] == 0 && !isSuggested[i][j]) { // Celda vacía y no sugerida
                    int validNumber = findValidSuggestion(i, j);
                    if (validNumber != -1) { // Si se encuentra una sugerencia válida
                        TextField txt = getTextFieldAtPosition(i, j);
                        if (txt != null) {
                            txt.setText(String.valueOf(validNumber));
                            txt.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, null, null)));  // Resaltar sugerencia
                            isSuggested[i][j] = true; // Marcar como sugerida
                        }
                        return; // Detener búsqueda después de hacer una sugerencia
                    }
                }
            }
        }

        // Si no hay sugerencias válidas disponibles
        new AlertBox().showMessage("Ayuda", "Sugerencia no disponible", "No se encontró ninguna sugerencia válida.");
    }

    // Método para encontrar una sugerencia válida en una celda vacía
    private int findValidSuggestion(int row, int col) {
        for (int number = 1; number <= 6; number++) {
            if (isValidSuggestion(row, col, number)) {
                return number; // Retornar el primer número que sea válido
            }
        }
        return -1; // No se encontró un número válido
    }

    // Validar una sugerencia contra todo el tablero
    private boolean isValidSuggestion(int row, int col, int number) {
        // Validar fila
        for (int c = 0; c < 6; c++) {
            if (sudokuPlayer[row][c] == number || sudoku.getSudoku()[row][c] == number) {
                return false;
            }
        }

        // Validar columna
        for (int r = 0; r < 6; r++) {
            if (sudokuPlayer[r][col] == number || sudoku.getSudoku()[r][col] == number) {
                return false;
            }
        }

        // Validar bloque 2x3
        int startRow = (row / 2) * 2;
        int startCol = (col / 3) * 3;
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if (sudokuPlayer[r][c] == number || sudoku.getSudoku()[r][c] == number) {
                    return false;
                }
            }
        }
        return true; // Cumple con todas las reglas del Sudoku
    }

    private TextField getTextFieldAtPosition(int row, int col) {
        for (var node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                return (TextField) node;
            }
        }
        return null;
    }

    @FXML
    public void onHandleButtonConfirm(ActionEvent event) {
        new AlertBox().showMessageInformation("Confirmación", "Estado del Juego", "Botón Confirmar presionado.");
    }

    @FXML
    public void onHandleButtonBackToPlay(ActionEvent actionEvent) throws IOException {
        GameStage.deleteInstance();
        WelcomeStage.getInstance();
    }

    private void verifyEmptyNumber(TextField txt, int n) {
        if (n != 0) {
            txt.setEditable(false);
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(240, 240, 240), null, null)));
        } else {
            txt.clear();
            txt.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), null, null)));
        }
    }

    public TextField textFieldStyle(TextField txt) {
        txt.setMaxWidth(70);  // Ajusta el ancho del TextField
        txt.setMaxHeight(70);  // Ajusta la altura del TextField
        txt.setFont(new Font("Verdana", 20));
        txt.setAlignment(Pos.CENTER);
        return txt;
    }
}
