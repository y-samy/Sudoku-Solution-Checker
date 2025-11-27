package com.github.y_samy.sudoku.base;

import org.jspecify.annotations.NonNull;

public interface SudokuGroup {
    enum GroupType {
        ROW,
        COLUMN,
        BOX;
    }

    /**
     * 
     * @return {@code @NonNull GroupType}: one of the following {@code "ROW"},
     *         {@code "COLUMN"}, {@code "BOX"}
     * 
     */
    @NonNull
    public GroupType getType();

    /**
     * 
     * @return {@code int}: a 1-to-9 number indicating the position of the group
     *         relative to the entire grid
     * 
     */
    public int getGlobalPosition();

    /**
     * 
     * @return {@code int @NonNull []}: an array whose length can be any integer
     *         from 0 to 9, where each present element is an integer representing a
     *         problematic position (1-9) for a cell in the given Sudoku group
     * 
     */
    public int @NonNull [] getCellsFlat();

    public @NonNull SudokuGroupValidationResult validate();
}