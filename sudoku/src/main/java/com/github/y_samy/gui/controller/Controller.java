package com.github.y_samy.gui.controller;

import java.io.IOException;

import com.github.y_samy.gui.model.Catalog;
import com.github.y_samy.gui.model.Driver;
import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.Loader;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;

import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;
import com.github.y_samy.sudoku.Solver;

public class Controller implements Viewable {

    private Loader loader;
    private Driver driver;
    private Catalog catalog;
    private Storage storage;
    private Game displayedGame;

    public Controller(Storage gameStorage) {
        loader = new Loader(gameStorage);
        driver = new Driver(gameStorage);
        catalog = new Catalog(gameStorage);
        storage = gameStorage;
    }

    @Override
    public Catalog getCatalog() {
        return catalog;
    }

    public Game getCurrentGame() throws NotFoundException {
        return loader.readCurrentGame();
    }

    @Override
    public Game getGame(DifficultyEnum level) throws NotFoundException {
        return loader.startNewGame(level);
    }

    @Override
    public void driveGames(Game sourceGame) throws SolutionInvalidException {
        if (!sourceGame.isValid())
            throw new SolutionInvalidException();
        try {
            driver.driveGames(sourceGame);
        } catch (IOException ex) {
            throw new SolutionInvalidException();
        }
    }

    public void driveGames(String sourceGame) throws SolutionInvalidException {
        driveGames(sourceGame);
    }

    @Override
    public String verifyGame(Game game) {
        var duplicates = game.getInvalid();
        if (duplicates.size() != 0) {
            return "invalid " + duplicates.toString();
        } else if (game.isValid()) {
            return "valid";
        } else {
            return "incomplete";
        }
    }

    @Override
    public int[] solveGame(Game game) throws InvalidGame {
        var solver = new Solver(game);
        solver.solveGame(game);
        return null;
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        storage.addUserAction(userAction);
    }

    @Override
    public void undoLastLog() throws IOException {
        storage.removeLastAction();
    }
}
