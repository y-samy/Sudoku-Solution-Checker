package com.github.y_samy.sudoku;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.validation.GroupValidationResult;

public class Group {
    public enum GroupType {
        ROW,
        COLUMN,
        BOX;
    }

    private final int cells @NonNull [];
    private final int globalPosition;
    private final @NonNull GroupType type;

    public Group(int @NonNull [] cells, int globalPosition, @NonNull GroupType type) {
        this.cells = cells;
        this.globalPosition = globalPosition;
        this.type = type;
    }

    /**
     * 
     * @return {@code @NonNull GroupType}: one of the following {@code "ROW"},
     *         {@code "COLUMN"}, {@code "BOX"}
     * 
     */
    @NonNull
    public GroupType getType() {
        return type;
    }

    /**
     * 
     * @return {@code int}: a 1-to-9 number indicating the position of the group
     *         relative to the entire grid
     * 
     */
    public int getGlobalPosition() {
        return globalPosition;
    }

    /**
     * 
     * @return {@code int @NonNull []}: an array whose length can be any integer
     *         from 0 to 9, where each present element is an integer representing a
     *         problematic position (1-9) for a cell in the given Sudoku group
     * 
     */
    public int @NonNull [] getCellsFlat() {
        return cells;
    }

    public @NonNull GroupValidationResult validate() {
        var positionMap = new HashMap<Integer, ArrayList<Integer>>(); // Cell Value : Cell Positions
        for (int i = 0; i < 9; i++) {
            var cellVal = cells[i];
            var posList = positionMap.get(cellVal);
            if (posList == null) {
                posList = new ArrayList<Integer>(0);
                positionMap.put(cellVal, posList);
            }
            posList.add(i + 1);
        }
        var invalidPositions = new ArrayList<Integer>(0);
        for (var positions : positionMap.values()) {
            if (positions.size() > 1)
                invalidPositions.addAll(positions);
        }
        @SuppressWarnings("null")
        @NonNull
        List<@NonNull Integer> invalidPositionsList = List.copyOf(invalidPositions);
        return new GroupValidationResult(invalidPositionsList, globalPosition, type);
    }
}