package com.github.y_samy.sudoku.base;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class SudokuGroupValidationResult {
    private final int @NonNull [] invalidCellPositions;

    public SudokuGroupValidationResult(int @Nullable [] invalidCellPositions) {
        if (invalidCellPositions == null)
            this.invalidCellPositions = new int[0];
        else
            this.invalidCellPositions = invalidCellPositions;
    }

    public boolean isValid() {
        return invalidCellPositions.length == 0;
    }

    public int @NonNull [] getInvalidCellPositions() {
        return invalidCellPositions;
    }
}
