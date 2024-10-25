package com.example.sudokugame.model;

public class Sudoku {
    private int[][] sudoku;

    public Sudoku() {
        // Celdas predefinidas del tablero de Sudoku
        this.sudoku = new int[][]{
                {0, 3, 0, 0, 0, 5},
                {6, 0, 0, 3, 0, 0},
                {0, 0, 6, 0, 0, 0},
                {3, 0, 0, 5, 0, 6},
                {0, 0, 4, 0, 0, 3},
                {2, 0, 0, 6, 0, 0}
        };
    }

    public int[][] getSudoku() {
        return sudoku;
    }

    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    // Validar que el movimiento no rompa las reglas del Sudoku
    public boolean isValidMove(int[][] board, int row, int col, int number) {
        // Verificar si el número ya está en la fila
        for (int c = 0; c < 6; c++) {
            if ((board[row][c] == number || sudoku[row][c] == number) && c != col) {
                return false;  // El número ya está en la fila (considerando celdas predefinidas)
            }
        }

        // Verificar si el número ya está en la columna
        for (int r = 0; r < 6; r++) {
            if ((board[r][col] == number || sudoku[r][col] == number) && r != row) {
                return false;  // El número ya está en la columna (considerando celdas predefinidas)
            }
        }

        // Verificar si el número ya está en el bloque 2x3
        int startRow = (row / 2) * 2;  // Primera fila del bloque 2x3
        int startCol = (col / 3) * 3;  // Primera columna del bloque 2x3
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((board[r][c] == number || sudoku[r][c] == number) && (r != row || c != col)) {
                    return false;  // El número ya está en el bloque 2x3 (considerando celdas predefinidas)
                }
            }
        }

        return true;  // Si pasa todas las verificaciones, el número es válido
    }

    // Verificar si el número no está repetido en la fila
    public boolean verifyRow(int row, int number) {
        for (int col = 0; col < 6; col++) {
            if (sudoku[row][col] == number) {
                return false;
            }
        }
        return true;
    }

    // Verificar si el número no está repetido en la columna
    public boolean verifyColumn(int col, int number) {
        for (int row = 0; row < 6; row++) {
            if (sudoku[row][col] == number) {
                return false;
            }
        }
        return true;
    }

    // Verificar si el número no está repetido en el bloque 2x3
    public boolean verifyQuadrant(int row, int col, int number) {
        int startRow = (row / 2) * 2;  // Primera fila del bloque 2x3
        int startCol = (col / 3) * 3;  // Primera columna del bloque 2x3
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (sudoku[i][j] == number) {
                    return false;
                }
            }
        }
        return true;
    }
}
