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
        return null;
    }

    @Override
    public int[][] solveGame(int[][] game) throws InvalidGame {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        controller.logUserAction(userAction.toString());
    }

}
