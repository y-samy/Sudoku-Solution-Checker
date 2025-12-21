package com.github.y_samy.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Game {

    private int referenceId;

    private final int[][] board;
    private Map<Integer, Integer> modifiableCells;
    private DifficultyEnum difficulty;
    private Stack<UserAction> oldProgress;
    private Stack<UserAction> newProgress;

    public static Game createSolutionBoard(int[][] board) {
        return new Game(board);
    }

    private Game(int[][] board) {
        this.board = board;
        referenceId = Math.abs(Arrays.deepHashCode(board));
    }

    Game(int[][] board, int[] modifiedCells) {
        this.board = board;
        int i = 0;
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                if (board[r][c] == 0)
                    modifiableCells.put(r * 9 + c, modifiedCells[i++]);
    }

    public int getReferenceId() {
        return referenceId;
    }

    public Game(int[][] board, DifficultyEnum difficulty) {
        this.board = board;
        this.difficulty = difficulty;
        modifiableCells = new HashMap<>(0);
        for (var r = 0; r < 9; r++)
            for (var c = 0; c < 9; c++)
                if (board[r][c] == 0)
                    modifiableCells.put(r * 9 + c, 0);
        oldProgress = new Stack<>();
        newProgress = new Stack<>();
    }

    public Game(int[][] board, DifficultyEnum difficulty, UserAction... progress) {
        this(board, difficulty);
        for (var action : progress) {
            oldProgress.push(action);
            coerceValue(action.row, action.row, action.currentValue);
        }
    }

    public int[][] getBoard() {
        return board;
    }

    public int[][] copyBoard() {
        var newBoard = new int[9][9];
        for (int r = 0; r < 9; r++)
            for (int c = 0; c < 9; c++)
                newBoard[r][c] = getValueAt(r, c);
        return newBoard;
    }

    public List<Integer> getEmptyCellPositions() {
        var l = new ArrayList<Integer>();
        for (var entry : modifiableCells.entrySet()) {
            if (entry.getValue() == 0)
                l.add(entry.getKey());
        }
        return l;
    }

    public int getValueAt(int row, int column) {
        var value = board[row][column];
        if (value != 0)
            return value;
        else
            return modifiableCells.get(row * 9 + column);
    }

    public boolean modifiable(int row, int column) {
        return modifiableCells.containsKey(row * 9 + column);
    }

    public void setValueAt(int row, int column, int value) {
        if (!modifiable(row, column))
            return;
        modifiableCells.put(row * 9 + column, value);
        newProgress.push(new UserAction(row, column, getValueAt(row, column), value));
    }

    private void coerceValue(int row, int column, int value) {
        if (!modifiable(row, column))
            return;
        modifiableCells.put(row * 9 + column, value);
    }

    public boolean canUndo(boolean includeOldProgress) {
        return !newProgress.isEmpty() | (includeOldProgress && !oldProgress.isEmpty());
    }

    public void undo(boolean includeOldProgress) {
        UserAction lastAction;
        if (!newProgress.isEmpty())
            lastAction = newProgress.pop();
        else if (includeOldProgress && !oldProgress.isEmpty())
            lastAction = oldProgress.pop();
        else
            return;
        coerceValue(lastAction.row, lastAction.column, lastAction.previousValue);
    }

    public DifficultyEnum getDifficulty() {
        return difficulty;
    }

    public List<Integer> getInvalidModifiable() {
        var results = new ArrayList<Integer>(0);
        var rowMasks = new int[9];
        var columnMasks = new int[9];
        var boxMasks = new int[9];
        for (var row = 0; row < 9; row++) {
            for (var col = 0; col < 9; col++) {
                int cellValue = getValueAt(row, col);

                if (cellValue == 0)
                    continue;

                // invalidity check
                int box = (row / 3) * 3 + (col / 3);
                int mask = 1 << (cellValue - 1);

                if ((rowMasks[row] & mask) != 0)
                    results.add(row * 9 + col);
                if ((columnMasks[col] & mask) != 0)
                    results.add(row * 9 + col);
                if ((boxMasks[box] & mask) != 0)
                    results.add(row * 9 + col);

                rowMasks[row] |= mask;
                columnMasks[col] |= mask;
                boxMasks[box] |= mask;
            }
        }
        return results;
    }

    public boolean isValid() {
        var rowMasks = new int[9];
        var columnMasks = new int[9];
        var boxMasks = new int[9];
        for (var row = 0; row < 9; row++) {
            for (var col = 0; col < 9; col++) {
                int cellValue = getValueAt(row, col);

                if (cellValue == 0) // incompleteness check
                    return false;

                // invalidity check
                int box = (row / 3) * 3 + (col / 3);
                int mask = 1 << (cellValue - 1);

                if ((rowMasks[row] & mask) != 0)
                    return false;
                if ((columnMasks[col] & mask) != 0)
                    return false;
                if ((boxMasks[box] & mask) != 0)
                    return false;

                rowMasks[row] |= mask;
                columnMasks[col] |= mask;
                boxMasks[box] |= mask;
            }
        }
        return true;
    }

}
