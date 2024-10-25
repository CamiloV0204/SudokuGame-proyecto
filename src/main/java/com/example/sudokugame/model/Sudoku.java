package com.example.sudokugame.model;

/** Authors:
 Camilo Vidales Lucumi -2342505
 Hector Ivan Sanchez - 2340635
 Emails:
 camilo.vidales@correounivalle.edu.co
 hector.ivan.sanchez@correounivalle.edu.co
 */

/**
 * Represents the model for the Sudoku game board.
 * Provides methods for checking if a move is valid according to Sudoku rules.
 */
public class Sudoku {
    private int[][] sudoku; // The Sudoku board

    /**
     * Constructor that initializes the Sudoku board with predefined values.
     * The cells with "0" represent empty spaces.
     */
    public Sudoku() {
        this.sudoku = new int[][]{
                {0, 3, 0, 0, 0, 5},
                {6, 0, 0, 3, 0, 0},
                {0, 0, 6, 0, 0, 0},
                {3, 0, 0, 5, 0, 6},
                {0, 0, 4, 0, 0, 3},
                {2, 0, 0, 6, 0, 0}
        };
    }

    /**
     * Returns the current Sudoku board.
     *
     * @return The 2D array representing the Sudoku board.
     */
    public int[][] getSudoku() {
        return sudoku;
    }

    /**
     * Sets a new Sudoku board.
     *
     * @param sudoku The new board to be set.
     */
    public void setSudoku(int[][] sudoku) {
        this.sudoku = sudoku;
    }

    /**
     * Validates whether a move is valid according to Sudoku rules.
     * A valid move does not duplicate a number in the row, column, or 2x3 block.
     *
     * @param board  The current state of the player's input.
     * @param row    The row index of the cell.
     * @param col    The column index of the cell.
     * @param number The number to validate.
     * @return true if the move is valid, false otherwise.
     */
    public boolean isValidMove(int[][] board, int row, int col, int number) {
        // Validate the row
        for (int c = 0; c < 6; c++) {
            if ((board[row][c] == number || sudoku[row][c] == number) && c != col) {
                return false; // The number is already in the row
            }
        }

        // Validate the column
        for (int r = 0; r < 6; r++) {
            if ((board[r][col] == number || sudoku[r][col] == number) && r != row) {
                return false; // The number is already in the column
            }
        }

        // Validate the 2x3 block
        int startRow = (row / 2) * 2;  // First row of the block
        int startCol = (col / 3) * 3;  // First column of the block
        for (int r = startRow; r < startRow + 2; r++) {
            for (int c = startCol; c < startCol + 3; c++) {
                if ((board[r][c] == number || sudoku[r][c] == number) && (r != row || c != col)) {
                    return false; // The number is already in the 2x3 block
                }
            }
        }

        return true; // The move is valid
    }

    /**
     * Validates if a number does not repeat in a specific row.
     *
     * @param row    The row index.
     * @param number The number to validate.
     * @return true if the number is not repeated, false otherwise.
     */
    public boolean verifyRow(int row, int number) {
        for (int col = 0; col < 6; col++) {
            if (sudoku[row][col] == number) {
                return false; // The number is already in the row
            }
        }
        return true;
    }

    /**
     * Validates if a number does not repeat in a specific column.
     *
     * @param col    The column index.
     * @param number The number to validate.
     * @return true if the number is not repeated, false otherwise.
     */
    public boolean verifyColumn(int col, int number) {
        for (int row = 0; row < 6; row++) {
            if (sudoku[row][col] == number) {
                return false; // The number is already in the column
            }
        }
        return true;
    }

    /**
     * Validates if a number does not repeat in the corresponding 2x3 block.
     *
     * @param row    The row of the cell to validate.
     * @param col    The column of the cell to validate.
     * @param number The number to validate.
     * @return true if the number is not repeated in the block, false otherwise.
     */
    public boolean verifyQuadrant(int row, int col, int number) {
        int startRow = (row / 2) * 2;  // First row of the block
        int startCol = (col / 3) * 3;  // First column of the block
        for (int i = startRow; i < startRow + 2; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (sudoku[i][j] == number) {
                    return false; // The number is already in the block
                }
            }
        }
        return true;
    }
}
