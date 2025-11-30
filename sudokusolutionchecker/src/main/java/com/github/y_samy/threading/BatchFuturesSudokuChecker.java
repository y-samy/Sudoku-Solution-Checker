package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;
import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;

public class BatchFuturesSudokuChecker extends SudokuCheckerFactory {

    @Override
    protected HashMap<GroupType, ArrayList<SudokuGroupValidationResult>>validate(int @NonNull [] @NonNull [] game, int concurrentTasksCount) {
        var executor = Executors.newFixedThreadPool(concurrentTasksCount);
        var awaitedFutures = new ArrayList<Future<?>>();
        var results = newResultMap();
        var batches = getBatches(game, concurrentTasksCount);
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
}
