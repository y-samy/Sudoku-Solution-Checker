package com.github.y_samy.validation;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.validation.base.BoardChecker;

public class BoardCheckerFactory {

    public enum ConcurrencyStrategy {
        THREADED,
        FUTURES,
        BATCH_FUTURES;
    }

    @NonNull
    public static BoardChecker newSequentialChecker(int @NonNull [] @NonNull [] game) {
        return new SequentialChecker(game);
    }

    @NonNull
    public static BoardChecker newConcurrentValidator(int @NonNull [] @NonNull [] game, int concurrentTasksCount) {
        return new BatchThreadedChecker(game, concurrentTasksCount);
    }

    @NonNull
    public static BoardChecker newConcurrentValidator(int @NonNull [] @NonNull [] game, int concurrentTasksCount,
            ConcurrencyStrategy strategy) {
        if (strategy == ConcurrencyStrategy.THREADED)
            return new BatchThreadedChecker(game, concurrentTasksCount);
        else if (strategy == ConcurrencyStrategy.FUTURES)
            return new FuturesChecker(game, concurrentTasksCount);
        else
            return new BatchFuturesChecker(game, concurrentTasksCount);
    }

}
