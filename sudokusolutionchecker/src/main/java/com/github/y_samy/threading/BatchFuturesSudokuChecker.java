package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;
import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;

public class BatchFuturesSudokuChecker implements SudokuThreadedCheckerFactory {

    private ArrayList<SudokuGroup> getGameGroups(int @NonNull [] @NonNull [] game) {
        var gameGroups = new ArrayList<SudokuGroup>();

        var gameFactory = new Sudoku();
        for (int i = 1; i <= 9; i++) {
            gameGroups.add(gameFactory.createRow(game, i));
            gameGroups.add(gameFactory.createColumn(game, i));
            gameGroups.add(gameFactory.createBox(game, i));
        }
        return gameGroups;
    }

    private HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newResultMap() {
        var results = new HashMap<GroupType, ArrayList<SudokuGroupValidationResult>>();
        var rows = new ArrayList<SudokuGroupValidationResult>();
        var cols = new ArrayList<SudokuGroupValidationResult>();
        var boxes = new ArrayList<SudokuGroupValidationResult>();
        results.put(GroupType.ROW, rows);
        results.put(GroupType.COLUMN, cols);
        results.put(GroupType.BOX, boxes);
        return results;
    }

    private ArrayList<List<SudokuGroup>> getBatches(
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

    private HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> batchVerifier(int @NonNull [] @NonNull [] game,
            int batchCount) {
        var executor = Executors.newFixedThreadPool(batchCount);
        var awaitedFutures = new ArrayList<Future<?>>();
        var results = newResultMap();
        var batches = getBatches(game, batchCount);
        for (var batch : batches) {
            awaitedFutures.add(executor.submit(() -> {
                batch.stream().forEach(
                        group -> results.get(group.getType()).add(group.validate()));
            }));
        }
        try {
            for (var fut : awaitedFutures)
                fut.get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        } finally {
            executor.shutdown();
        }
        return results;
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newSingleThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return batchVerifier(game, 1);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newThreeThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return batchVerifier(game, 3);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newNineThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return batchVerifier(game, 9);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return batchVerifier(game, 27);
    }
}
