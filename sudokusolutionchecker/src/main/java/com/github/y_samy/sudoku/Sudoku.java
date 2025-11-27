package com.github.y_samy.sudoku;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupFactory;

public class Sudoku implements SudokuGroupFactory {

    @Override
    public @NonNull SudokuGroup createRow(int @NonNull [] @NonNull [] game, int position) {
        return new SudokuGroup(game[position - 1], position, SudokuGroup.GroupType.ROW);
    }

    @Override
    public @NonNull SudokuGroup createColumn(int @NonNull [] @NonNull [] game, int position) {
        return new SudokuGroup(getColumnAt(game, position), position, SudokuGroup.GroupType.COLUMN);
    }

    @Override
    public @NonNull SudokuGroup createBox(int @NonNull [] @NonNull [] game, int position) {
        return new SudokuGroup(getBoxAt(game, position), position, SudokuGroup.GroupType.BOX);
    }

    private int @NonNull [] getColumnAt(int @NonNull [] @NonNull [] game, int position) {
        var col = new int[9];
        for (int i = 0; i < 9; i++) {
            col[i] = game[position - 1][i];
        }
        return col;
    }

    private int @NonNull [] getBoxAt(int @NonNull [] @NonNull [] game, int position) {
        var box = new int[9];
        // box 0: rows 0 -> 2, cols 0 -> 2;
        // box 1: rows 0 -> 2, cols 3 -> 5;
        // box 2: rows 0 -> 2, cols 6 -> 8;
        // box 3: rows 3 -> 5, cols 0 -> 2;
        // idx = position - 1;
        // colStartIdx = (box % 3) * 3;
        // rowStartIdx = (box / 3) * 3;
        position -= 1;
        int rowStartIdx = (position / 3) * 3;
        int colStartIdx = (position % 3) * 3;
        int i = 0;
        for (int r = rowStartIdx; r < rowStartIdx + 3; r += 1) {
            for (int c = colStartIdx; c < colStartIdx + 3; c += 1) {
                box[i] = game[r][c];
                i += 1;
            }
        }
        return box;
    }
}
