package com.github.y_samy.validation;

import java.util.ArrayList;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.validation.base.BoardChecker;

public class SequentialChecker extends BoardChecker {

    public SequentialChecker(int @NonNull [] @NonNull [] board) {
        super(board);
    }

    @Override
    public @Nullable ArrayList<GroupValidationResult> validate() {
        var results = new ArrayList<GroupValidationResult>();
        board.forEach(group -> results.add(group.validate()));
        return results;
    }

}
