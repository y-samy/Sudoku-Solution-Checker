package com.github.y_samy.gui.model;

import java.io.IOException;
import java.util.Arrays;

import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;
import com.github.y_samy.sudoku.UserAction;

public class Loader {
    Storage storage;

    public Loader(Storage gameStorage) {
        storage = gameStorage;
    }

    public Game readCurrentGame() throws NotFoundException {
        try {
            var board = storage.readCurrentPuzzle();
            var progress = storage.readCurrentProgress().stream()
                    .map(log -> Arrays.stream(log.replace("(", "").replace(")", "").split("(\\s*),(\\s*)"))
                            .map(Integer::parseInt).toArray(Integer[]::new))
                    .map(arr -> new UserAction(arr[0], arr[1], arr[2], arr[3])).toArray(UserAction[]::new);
            var difficulty = DifficultyEnum.valueOf(storage.getCurrentGameDifficulty());
            return new Game(board, difficulty, progress);
        } catch (IOException ex) {
            throw new NotFoundException();
        }
    }

    public Game startNewGame(DifficultyEnum difficulty) throws NotFoundException {
        try {
            return new Game(storage.loadAndStartPuzzle(difficulty.name()), difficulty);
        } catch (IOException ex) {
            throw new NotFoundException();
        }
    }
}
