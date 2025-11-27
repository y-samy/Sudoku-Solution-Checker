package com.github.y_samy.sudoku.base;

import org.jspecify.annotations.NonNull;

public interface SudokuGroupFactory {
    @NonNull
    public SudokuGroup createRow(int @NonNull [] @NonNull [] game, int position);

    @NonNull
    public SudokuGroup createColumn(int @NonNull [] @NonNull [] game, int position);

    @NonNull
    public SudokuGroup createBox(int @NonNull [] @NonNull [] game, int position);
}
