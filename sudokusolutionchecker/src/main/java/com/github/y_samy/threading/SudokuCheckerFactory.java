package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public abstract class SudokuCheckerFactory {

    protected abstract HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> validate(
            int @NonNull [] @NonNull [] game, int concurrentTasksCount);

    protected static final HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> validateSequential(
            int @NonNull [] @NonNull [] game) {
        var results = newResultMap();
        var groups = getGameGroups(game);
        groups.forEach(group -> results.get(group.getType()).add(group.validate()));
        return results;
    }

    @Nullable
    public final static HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newSingleThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validateSequential(game);
    }

    @Nullable
    public final HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newThreeThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validate(game, 3);
    }

    @Nullable
    public final HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newNineThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validate(game, 9);
    }

    @Nullable
    public final HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validate(game, 27);
    }

    protected static final ArrayList<SudokuGroup> getGameGroups(int @NonNull [] @NonNull [] game) {
        var gameGroups = new ArrayList<SudokuGroup>();

        var gameFactory = new Sudoku();
        for (int i = 1; i <= 9; i++) {
            gameGroups.add(gameFactory.createRow(game, i));
            gameGroups.add(gameFactory.createColumn(game, i));
            gameGroups.add(gameFactory.createBox(game, i));
        }
        return gameGroups;
    }

    protected static final HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newResultMap() {
        var results = new HashMap<GroupType, ArrayList<SudokuGroupValidationResult>>();
        var rows = new ArrayList<SudokuGroupValidationResult>();
        var cols = new ArrayList<SudokuGroupValidationResult>();
        var boxes = new ArrayList<SudokuGroupValidationResult>();
        results.put(GroupType.ROW, rows);
        results.put(GroupType.COLUMN, cols);
        results.put(GroupType.BOX, boxes);
        return results;
    }

    protected static final ArrayList<List<SudokuGroup>> getBatches(
            int @NonNull [] @NonNull [] game, int batchCount) {
        var groups = getGameGroups(game);
        var batches = new ArrayList<List<SudokuGroup>>();
        var batchSize = groups.size() / batchCount;
        for (int i = 0; i < batchCount; i++) {
            int startIndex = i * batchSize;
            int endIndex = (i + 1) * batchSize;
            batches.add(groups.subList(startIndex, endIndex));
        }
        return batches;
    }
}
