/*
 * OOP Principles: Encapsulation keeps board data private, and Single Responsibility
 * keeps this class focused only on Tic-Tac-Toe board rules.
 */

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    public static final char EMPTY = ' ';
    public static final char PLAYER_X = 'X';
    public static final char PLAYER_O = 'O';

    private char[][] grid;
    private int size;
    private int moveCount;

    public GameBoard(int size) {
        if (size < 3 || size > 5) {
            throw new IllegalArgumentException("Board size must be 3, 4, or 5.");
        }
        this.size = size;
        this.grid = new char[size][size];
        initBoard();
    }

    public void initBoard() {
        moveCount = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                grid[row][col] = EMPTY;
            }
        }
    }

    public boolean makeMove(int index, char mark) {
        if (index < 0 || index >= size * size) {
            return false;
        }
        int row = index / size;
        int col = index % size;
        if (grid[row][col] != EMPTY) {
            return false;
        }
        grid[row][col] = mark;
        moveCount++;
        return true;
    }

    public void undoMove(int index) {
        if (index < 0 || index >= size * size) {
            return;
        }
        int row = index / size;
        int col = index % size;
        if (grid[row][col] != EMPTY) {
            grid[row][col] = EMPTY;
            moveCount--;
        }
    }

    public boolean checkWin(char mark) {
        return !getWinningCells(mark).isEmpty();
    }

    public List<Integer> getWinningCells(char mark) {
        List<Integer> winningCells = new ArrayList<Integer>();
        for (int row = 0; row < size; row++) {
            boolean rowWin = true;
            for (int col = 0; col < size; col++) {
                if (grid[row][col] != mark) {
                    rowWin = false;
                    break;
                }
            }
            if (rowWin) {
                for (int col = 0; col < size; col++) {
                    winningCells.add(Integer.valueOf(row * size + col));
                }
                return winningCells;
            }
        }

        for (int col = 0; col < size; col++) {
            boolean colWin = true;
            for (int row = 0; row < size; row++) {
                if (grid[row][col] != mark) {
                    colWin = false;
                    break;
                }
            }
            if (colWin) {
                for (int row = 0; row < size; row++) {
                    winningCells.add(Integer.valueOf(row * size + col));
                }
                return winningCells;
            }
        }

        boolean mainDiagonalWin = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][i] != mark) {
                mainDiagonalWin = false;
                break;
            }
        }
        if (mainDiagonalWin) {
            for (int i = 0; i < size; i++) {
                winningCells.add(Integer.valueOf(i * size + i));
            }
            return winningCells;
        }

        boolean antiDiagonalWin = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][size - 1 - i] != mark) {
                antiDiagonalWin = false;
                break;
            }
        }
        if (antiDiagonalWin) {
            for (int i = 0; i < size; i++) {
                winningCells.add(Integer.valueOf(i * size + (size - 1 - i)));
            }
        }
        return winningCells;
    }

    public boolean isFull() {
        return moveCount == size * size;
    }

    public List<Integer> getEmptyCells() {
        List<Integer> emptyCells = new ArrayList<Integer>();
        for (int index = 0; index < size * size; index++) {
            if (getCell(index) == EMPTY) {
                emptyCells.add(Integer.valueOf(index));
            }
        }
        return emptyCells;
    }

    public char getCell(int index) {
        if (index < 0 || index >= size * size) {
            return EMPTY;
        }
        return grid[index / size][index % size];
    }

    public int getSize() {
        return size;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
