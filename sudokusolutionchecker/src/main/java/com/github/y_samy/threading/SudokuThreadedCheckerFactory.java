package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public interface SudokuThreadedCheckerFactory {
    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newSingleThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newThreeThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newNineThreadValidator(int @NonNull [] @NonNull [] game);

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game);
}
