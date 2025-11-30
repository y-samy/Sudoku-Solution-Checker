package com.github.y_samy.validation.base;

import java.util.ArrayList;
import java.util.List;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.Group;

public abstract class BatchChecker extends BoardChecker {
    protected ArrayList<List<Group>> batches;

    protected BatchChecker(int @NonNull [] @NonNull [] board, int batchCount) {
        super(board);
        var batchSize = this.board.size() / batchCount;
        for (int i = 0; i < batchCount; i++) {
            int startIndex = i * batchSize;
            int endIndex = (i + 1) * batchSize;
            batches.add(this.board.subList(startIndex, endIndex));
        }
    }
}
