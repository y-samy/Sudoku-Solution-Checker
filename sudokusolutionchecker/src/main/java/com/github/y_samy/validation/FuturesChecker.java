package com.github.y_samy.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.base.BoardChecker;

public class FuturesChecker extends BoardChecker {
    private ExecutorService executor;

    public FuturesChecker(int @NonNull [] @NonNull [] board, int concurrentTasksCount) {
        super(board);
        executor = Executors.newFixedThreadPool(concurrentTasksCount);
    }

    @Override
    public @Nullable HashMap<GroupType, ArrayList<GroupValidationResult>> validate() {
        var awaitedFutures = new ArrayList<Future<?>>();
        var results = newResultMap();
        board.forEach(group -> {
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
