package com.github.y_samy.validation.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.GroupValidationResult;
import com.github.y_samy.sudoku.Group;
import com.github.y_samy.sudoku.GroupFactory;

public abstract class BoardChecker {
    protected ArrayList<Group> board;
    protected ArrayList<GroupValidationResult> results;

    @Nullable
    public abstract ArrayList<GroupValidationResult> validate();

    protected BoardChecker(int @NonNull [] @NonNull [] board) {
        this.board = GroupFactory.getBoard(board);
    }

    public static final HashMap<GroupType, List<GroupValidationResult>> sortResults(
            ArrayList<GroupValidationResult> results) {
        var sorted = new HashMap<GroupType, List<GroupValidationResult>>();
        sorted.put(GroupType.ROW,
                results.stream().sorted(Comparator.comparing(GroupValidationResult::getGlobalPosition))
                        .filter(group -> group.getGroupType() == GroupType.ROW).toList());
        sorted.put(GroupType.COLUMN,
                results.stream().sorted(Comparator.comparing(GroupValidationResult::getGlobalPosition))
                        .filter(group -> group.getGroupType() == GroupType.COLUMN).toList());
        sorted.put(GroupType.BOX,
                results.stream().sorted(Comparator.comparing(GroupValidationResult::getGlobalPosition))
                        .filter(group -> group.getGroupType() == GroupType.BOX).toList());
        return sorted;
    }
}
