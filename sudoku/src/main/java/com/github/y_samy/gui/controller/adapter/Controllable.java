package com.github.y_samy.gui.controller.adapter;

import java.io.IOException;

import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.sudoku.UserAction;

// Adapter interface
public interface Controllable {
    public boolean[] getCatalog();

    public int[][] getGame(char level) throws NotFoundException;

    public void driveGames(String sourcePath) throws SolutionInvalidException;

    public boolean[][] verifyGame(int[][] game);

    public int[][] solveGame(int[][] game) throws InvalidGame;

    public void logUserAction(UserAction userAction) throws IOException;
}
