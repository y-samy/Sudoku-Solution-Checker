package com.github.y_samy.sudoku;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class SudokuBox implements SudokuGroup {
    private final int cells @NonNull [];
    private final int globalPosition;

    public SudokuBox(int @NonNull [] cells, int globalPosition) {
        this.cells = cells;
        this.globalPosition = globalPosition;
    }

    @Override
    public @NonNull GroupType getType() {
        return GroupType.BOX;
    }

    @Override
    public int getGlobalPosition() {
        return globalPosition;
    }

    @Override
    public int @NonNull [] getCellsFlat() {
        return cells;
    }

    @Override
    public @NonNull SudokuGroupValidationResult validate() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

}
