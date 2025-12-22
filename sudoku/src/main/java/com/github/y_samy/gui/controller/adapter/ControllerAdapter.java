package com.github.y_samy.gui.controller.adapter;

import java.io.IOException;
import java.util.Arrays;

import com.github.y_samy.gui.controller.Viewable;
import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;
import com.github.y_samy.sudoku.UserAction;

public class ControllerAdapter implements Controllable {

    private Viewable controller;
    private Storage storage;

    public ControllerAdapter(Viewable controller, Storage gameStorage) {
        this.controller = controller;
        this.storage = gameStorage;
    }

    @Override
    public boolean[] getCatalog() {
        var catalog = controller.getCatalog();
        try {
            return new boolean[] { catalog.current(), catalog.allModesExist() };
        } catch (IOException e) {
            return new boolean[] { false, false };
        }
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        @SuppressWarnings("null")
        var matchingDifficulty = Arrays.stream(DifficultyEnum.values())
                .filter(d -> d.name().toLowerCase().charAt(0) == Character.toLowerCase(level))
                .findFirst().get();
        return controller.getGame(matchingDifficulty).getBoard();
    }

    @Override
    public void driveGames(String sourcePath) throws SolutionInvalidException {
        try {
            Game.createSolutionBoard(storage.loadSolvedBoard(sourcePath));
        } catch (IOException e) {
            throw new SolutionInvalidException();
        }
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        var validityArray = new boolean[9][9];
        var promotedGame = Game.createSolutionBoard(game);
        var validityString = controller.verifyGame(promotedGame);
        if ("incomplete".equals(validityString)) {
            var incomplete = promotedGame.getEmptyCellPositions();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    validityArray[r][c] = !incomplete.contains(r * 9 + c);
                }
            }
        } else if ("valid".equals(validityString)) {
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    validityArray[r][c] = true;
                }
            }
        } else {
            var duplicates = Arrays.stream(
                    validityString.replace("invalid ", "").replace("[", "").replace("]", "").split("(\\s*),(\\s*)"))
                    .map(Integer::parseInt).toList();
            for (int r = 0; r < 9; r++) {
                for (int c = 0; c < 9; c++) {
                    validityArray[r][c] = !duplicates.contains(r * 9 + c);
                }
            }
        }
        return validityArray;
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        var oldGame = Game.createSolutionBoard(game);
        var empty = oldGame.getEmptyCellPositions();
        var oldBoard = oldGame.copyBoard();
        var solvedGame = controller.solveGame(oldGame);
        int i = 0;
        for (var idx : empty) {
            oldBoard[idx / 9][idx % 9] = solvedGame[i++];
        }
        return oldBoard;
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction.toString());
    }

}
