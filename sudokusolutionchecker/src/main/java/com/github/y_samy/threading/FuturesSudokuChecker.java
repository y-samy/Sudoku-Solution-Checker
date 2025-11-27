package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class FuturesSudokuChecker implements SudokuThreadedCheckerFactory {

    private ArrayList<SudokuGroup> getGameGroups(int @NonNull [] @NonNull [] game) {
        var gameGroups = new ArrayList<SudokuGroup>(9);
        var gameFactory = new Sudoku();
        for (int i = 1; i <= 9; i++) {
            gameGroups.add(gameFactory.createRow(game, i));
        }
        for (int i = 1; i <= 9; i++) {
            gameGroups.add(gameFactory.createColumn(game, i));
        }
        for (int i = 1; i <= 9; i++) {
            gameGroups.add(gameFactory.createBox(game, i));
        }
        return gameGroups;
    }

    private @Nullable ArrayList<@NonNull SudokuGroupValidationResult> validateThreaded(int @NonNull [] @NonNull [] game,
            int threads) {
        var executor = Executors.newFixedThreadPool(threads);
        var groups = getGameGroups(game);
        var futureResults = new ArrayList<Future<SudokuGroupValidationResult>>();
        var results = new ArrayList<SudokuGroupValidationResult>();
        for (var group : groups) {
            futureResults.add(executor.submit(group::validate));
        }
        try {
            for (var fr : futureResults) {
                results.add(fr.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            return null;
        } finally {
            executor.shutdown();
        }
        return results;
    }

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newSingleThreadValidator(int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 1);
    }

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newThreeThreadValidator(int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 3);
    }

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newNineThreadValidator(int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 9);
    }

    @Nullable
    public ArrayList<@NonNull SudokuGroupValidationResult> newTwentySevenThreadValidator(
            int @NonNull [] @NonNull [] game) {
        return validateThreaded(game, 27);
    }
}
