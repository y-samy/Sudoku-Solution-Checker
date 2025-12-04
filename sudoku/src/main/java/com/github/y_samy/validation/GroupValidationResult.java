package com.github.y_samy.validation;

import java.util.List;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.Group.GroupType;

public class GroupValidationResult {
    private final @NonNull List<@NonNull Integer> invalidCellPositions;
    private final int globalPosition;
    private final @NonNull GroupType type;

    public GroupValidationResult(@NonNull List<@NonNull Integer> invalidCellPositions, int globalPosition,
            @NonNull GroupType type) {

        this.invalidCellPositions = invalidCellPositions;
        this.globalPosition = globalPosition;
        this.type = type;
    }

    public int getGlobalPosition() {
        return globalPosition;
    }

    public GroupType getGroupType() {
        return type;
    }

    public boolean isValid() {
        return invalidCellPositions.size() == 0;
    }

    public @NonNull List<@NonNull Integer> getInvalidCellPositions() {
        return invalidCellPositions;
    }
}
