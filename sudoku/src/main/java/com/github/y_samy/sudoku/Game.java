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
    private boolean playable;

    public static Game createSolutionBoard(int[][] board) {
        return new Game(board);
    }

    private Game(int[][] board) {
        playable = false;
        this.board = board;
        referenceId = Math.abs(Arrays.deepHashCode(board));
    }

    Game(int[][] board, int[] modifiedCells) {
        playable = false;
        this.board = board;
        this.modifiableCells = new HashMap<>();
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
        playable = true;
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
            coerceValue(action.row, action.column, action.currentValue);
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
        if (!playable)
            return false;
        return modifiableCells.containsKey(row * 9 + column);
    }

    public void setValueAt(int row, int column, int value) {
        if (!modifiable(row, column))
            return;
        newProgress.push(new UserAction(row, column, value, getValueAt(row, column)));
        modifiableCells.put(row * 9 + column, value);
    }

    private void coerceValue(int row, int column, int value) {
        if (!modifiable(row, column))
            return;
        modifiableCells.put(row * 9 + column, value);
    }

    public boolean canUndo(boolean includeOldProgress) {
        if (!playable)
            return false;
        return !newProgress.isEmpty() | (includeOldProgress && !oldProgress.isEmpty());
    }

    public void undo(boolean includeOldProgress) {
        if (!playable)
            return;
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

    public List<Integer> getInvalid() {
        var results = new ArrayList<Integer>(0);
        var rowMasks = new int[9];
        var columnMasks = new int[9];
        var boxMasks = new int[9];

        var rowFirstOccurrence = new int[9][10]; // [row][value] -> index
        var colFirstOccurrence = new int[9][10]; // [col][value] -> index
        var boxFirstOccurrence = new int[9][10]; // [box][value] -> index

        for (int i = 0; i < 9; i++) {
            for (int v = 0; v <= 9; v++) {
                rowFirstOccurrence[i][v] = -1;
                colFirstOccurrence[i][v] = -1;
                boxFirstOccurrence[i][v] = -1;
            }
        }

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int cellValue = getValueAt(row, col);
                if (cellValue == 0)
                    continue;

                int box = (row / 3) * 3 + (col / 3);
                int mask = 1 << (cellValue - 1);
                int idx = row * 9 + col;

                boolean isDuplicate = false;

                if ((rowMasks[row] & mask) != 0) {
                    isDuplicate = true;
                    int firstIdx = rowFirstOccurrence[row][cellValue];
                    if (!results.contains(firstIdx)) {
                        results.add(firstIdx);
                    }
                } else {
                    rowFirstOccurrence[row][cellValue] = idx;
                }

                if ((columnMasks[col] & mask) != 0) {
                    isDuplicate = true;
                    int firstIdx = colFirstOccurrence[col][cellValue];
                    if (!results.contains(firstIdx)) {
                        results.add(firstIdx);
                    }
                } else {
                    colFirstOccurrence[col][cellValue] = idx;
                }

                if ((boxMasks[box] & mask) != 0) {
                    isDuplicate = true;
                    int firstIdx = boxFirstOccurrence[box][cellValue];
                    if (!results.contains(firstIdx)) {
                        results.add(firstIdx);
                    }
                } else {
                    boxFirstOccurrence[box][cellValue] = idx;
                }

                if (isDuplicate && !results.contains(idx)) {
                    results.add(idx);
                }

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
