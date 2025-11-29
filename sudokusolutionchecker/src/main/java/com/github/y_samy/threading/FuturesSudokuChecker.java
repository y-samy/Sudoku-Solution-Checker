package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class FuturesSudokuChecker implements SudokuThreadedCheckerFactory {

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

    private HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> validateThreaded(
            int @NonNull [] @NonNull [] game,
            int threads) {
        var executor = Executors.newFixedThreadPool(threads);
        var awaitedFutures = new ArrayList<Future<?>>();
        var results = newResultMap();
        var groups = getGameGroups(game);
        groups.forEach(group -> {
            awaitedFutures.add(executor.submit(() -> {
                results.get(group.getType()).add(group.validate());
            }));
        });
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
        return validateThreaded(game, 1);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newThreeThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 3);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newNineThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 9);
    }

    @Nullable
    public HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 27);
    }
}
