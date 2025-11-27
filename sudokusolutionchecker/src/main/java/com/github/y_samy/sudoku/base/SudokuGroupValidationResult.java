package com.github.y_samy.sudoku.base;

import org.jspecify.annotations.NonNull;

public interface SudokuGroupValidationResult {
    public boolean isValid();

    public int @NonNull [] getInvalidCellPositions();
}
