package com.github.y_samy.threading;

import java.util.ArrayList;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupFactory;

public class FuturesThreadedChecker implements ThreadedCheckerFactory {

    public void newSingleThreadValidator(int @NonNull [] @NonNull [] game) {
        ArrayList<SudokuGroup> gameGroups = new ArrayList<>(9);
        ArrayList<int[]> results = new ArrayList<>();
        Sudoku gameFactory = new Sudoku();
        for (int i = 0; i < 9; i++) {
            gameGroups.add(gameFactory.createRow(game, i));
        }
        for (int i = 0; i < 9; i++) {
            gameGroups.add(gameFactory.createColumn(game, i));
        }
        for (int i = 0; i < 9; i++) {
            gameGroups.add(gameFactory.createBox(game, i));
        }
    }
}
