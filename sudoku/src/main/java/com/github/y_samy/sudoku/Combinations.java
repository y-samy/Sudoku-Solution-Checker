package com.github.y_samy.sudoku;

public class Combinations implements Iterable<int[]> {

    private CombinationsIterator iterator = new CombinationsIterator();

    @Override
    public CombinationsIterator iterator() {
        return iterator;
    }

}
