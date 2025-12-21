package com.github.y_samy.gui.model;

import java.io.IOException;

import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;
import com.github.y_samy.sudoku.PuzzleFactory;

public class Driver {
    private Storage storage;

    public Driver(Storage gameStorage) {
        storage = gameStorage;
    }

    public void driveGames(Game source) throws IOException {
        for (var difficulty : DifficultyEnum.values()) {
            var puzzle = PuzzleFactory.generatePuzzle(source, difficulty);
            storage.savePuzzle(difficulty.name(), String.valueOf(source.getReferenceId()), puzzle);
        }
    }
}
