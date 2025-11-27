package com.github.y_samy.threading;

import java.util.ArrayList;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.sudoku.Sudoku;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;

public class FuturesThreadedChecker implements ThreadedCheckerFactory {

    public void newSingleThreadValidator(int @NonNull [] @NonNull [] game) {
        var gameGroups = new ArrayList<SudokuGroup>(9);
        var results = new ArrayList<SudokuGroupValidationResult>();
        var gameFactory = new Sudoku();
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
