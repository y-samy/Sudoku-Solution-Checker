package com.github.y_samy.gui.controller;

import java.io.IOException;

import com.github.y_samy.gui.model.Catalog;
import com.github.y_samy.gui.model.Driver;
import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.Loader;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.gui.view.board.BoardView;
import com.github.y_samy.gui.view.keypad.KeypadView;
import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;

public class MasterController implements Viewable {

    private KeypadView keypadView;
    private BoardView boardView;
    private Loader loader;
    private Driver driver;
    private Catalog catalog;
    private Storage storage;

    public MasterController(Storage gameStorage) {
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
        try {
            driveGames(Game.createSolutionBoard(storage.loadSolvedBoard(sourceGame)));
        } catch (IOException e) {
            throw new SolutionInvalidException();
        }

    }

    @Override
    public String verifyGame(Game game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyGame'");
    }

    @Override
    public int[] solveGame(Game game) throws InvalidGame {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }

    @Override
    public void logUserAction(String userAction) throws IOException {
        storage.addUserAction(userAction);
    }

    public void undoLastLog() throws IOException {
        storage.removeLastAction();
    }

}
