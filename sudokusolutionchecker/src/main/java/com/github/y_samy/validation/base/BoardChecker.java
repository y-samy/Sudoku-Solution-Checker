package com.github.y_samy.validation.base;

import java.util.ArrayList;
import java.util.HashMap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.GroupValidationResult;
import com.github.y_samy.sudoku.Group;
import com.github.y_samy.sudoku.GroupFactory;

public abstract class BoardChecker {
    protected ArrayList<Group> board;
    protected HashMap<GroupType, ArrayList<GroupValidationResult>> results;

    @Nullable
    public abstract HashMap<GroupType, ArrayList<GroupValidationResult>> validate();

    protected BoardChecker(int @NonNull [] @NonNull [] board) {
        this.board = GroupFactory.getBoard(board);
    }

    protected static final HashMap<GroupType, ArrayList<GroupValidationResult>> newResultMap() {
        var results = new HashMap<GroupType, ArrayList<GroupValidationResult>>();
        var rows = new ArrayList<GroupValidationResult>();
        var cols = new ArrayList<GroupValidationResult>();
        var boxes = new ArrayList<GroupValidationResult>();
        results.put(GroupType.ROW, rows);
        results.put(GroupType.COLUMN, cols);
        results.put(GroupType.BOX, boxes);
        return results;
    }
}
