package com.github.y_samy.validation;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.validation.base.BoardChecker;

public class BoardCheckerFactory {


    @NonNull
    public static BoardChecker newSequentialChecker(int @NonNull [] @NonNull [] game) {
        return new SequentialChecker(game);
    }

    @NonNull
    public static BoardChecker newConcurrentChecker(int @NonNull [] @NonNull [] game, int concurrentTasksCount) {
        return new BatchThreadedChecker(game, concurrentTasksCount);
    }

}
