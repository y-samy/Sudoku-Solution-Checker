package com.github.y_samy.sudoku;

import java.util.Iterator;

public class CombinationsIterator implements Iterator<int[]> {
    private static int LOWER = 1_1_1_1_1;
    private static int UPPER = 9_9_9_9_9;
    private int combinations = LOWER;

    @Override
    public boolean hasNext() {
        return combinations < UPPER;
    }

    @Override
    public int[] next() {
        var next = new int[5];
        combinations += 1;
        var nextInt = combinations;
        for (int i = 0; i < 5; i++) {
            next[i] = nextInt % 10;
            nextInt /= 10;
        }
        return next;
    }
}
