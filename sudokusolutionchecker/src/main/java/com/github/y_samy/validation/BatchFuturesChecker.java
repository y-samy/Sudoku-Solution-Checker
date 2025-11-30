package com.github.y_samy.validation;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.validation.base.BatchChecker;

public class BatchFuturesChecker extends BatchChecker {
    private ExecutorService executor;

    protected BatchFuturesChecker(int @NonNull [] @NonNull [] board, int concurrentTasksCount) {
        super(board, concurrentTasksCount);
        executor = Executors.newFixedThreadPool(concurrentTasksCount);
    }

    @Override
    public @Nullable ArrayList<GroupValidationResult> validate() {
        var awaitedFutures = new ArrayList<Future<?>>();
        results = new ArrayList<GroupValidationResult>();
        for (var batch : batches) {
            awaitedFutures.add(executor.submit(() -> {
                batch.stream().forEach(
                        group -> results.add(group.validate()));
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
