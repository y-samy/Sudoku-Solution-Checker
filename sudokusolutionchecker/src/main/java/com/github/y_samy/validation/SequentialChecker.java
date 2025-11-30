package com.github.y_samy.validation;

import java.util.ArrayList;
import java.util.HashMap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.base.BoardChecker;

public class SequentialChecker extends BoardChecker {

    public SequentialChecker(int @NonNull [] @NonNull [] board) {
        super(board);
    }

    @Override
    public @Nullable HashMap<GroupType, ArrayList<GroupValidationResult>> validate() {
        var results = newResultMap();
        board.forEach(group -> results.get(group.getType()).add(group.validate()));
        return results;
    }

}
