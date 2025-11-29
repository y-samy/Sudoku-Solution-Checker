package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class FuturesSudokuChecker extends SudokuCheckerFactory {

    @Override
    protected HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> validate(
            int @NonNull [] @NonNull [] game,
            int concurrentTasks) {
        var executor = Executors.newFixedThreadPool(concurrentTasks);
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
}
