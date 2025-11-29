package com.github.y_samy.threading;

import java.util.ArrayList;
import java.util.HashMap;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class BatchThreadedSudokuChecker extends SudokuCheckerFactory {

    @Override
    protected HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> validate(int @NonNull [] @NonNull [] game,
            int concurrentTasksCount) {

        var results = newResultMap();
        var batches = getBatches(game, concurrentTasksCount);
        var threads = new ArrayList<Thread>(concurrentTasksCount);
        for (var batch : batches) {
            var t = new Thread(() -> {
                batch.stream().forEach(group -> results.get(group.getType()).add(group.validate()));
            });
            threads.add(t);
            t.start();

        }
        try {
            for (var t : threads)
                t.join();
        } catch (InterruptedException e) {
            return null;
        }
        return results;
    }
}
