package com.github.y_samy.validation;

import java.util.ArrayList;
import java.util.HashMap;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.base.BatchChecker;

public class BatchThreadedChecker extends BatchChecker {
    private ArrayList<Thread> threads;

    protected BatchThreadedChecker(int @NonNull [] @NonNull [] board, int concurrentTasksCount) {
        super(board, concurrentTasksCount);
        threads = new ArrayList<Thread>(concurrentTasksCount);
        for (var batch : batches) {
            var t = new Thread(() -> {
                batch.stream().forEach(group -> results.get(group.getType()).add(group.validate()));
            });
            threads.add(t);
        }
    }

    @Override
    public @Nullable HashMap<GroupType, ArrayList<GroupValidationResult>> validate() {
        results = newResultMap();
        for (var t : threads)
            t.start();

        try {
            for (var t : threads)
                t.join();
        } catch (InterruptedException e) {
            return null;
        }
        return results;
    }

}
