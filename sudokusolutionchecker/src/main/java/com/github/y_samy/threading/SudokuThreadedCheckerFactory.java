package com.github.y_samy.threading;

import java.util.ArrayList;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public interface SudokuThreadedCheckerFactory {
    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newSingleThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newThreeThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newNineThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game);
}
