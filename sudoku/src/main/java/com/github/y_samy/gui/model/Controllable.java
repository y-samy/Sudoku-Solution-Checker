package com.github.y_samy.gui.view;

import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.sudoku.UserAction;

public interface Controllable {
    public boolean[] getCatalog();

    public int[][] getGame(char level) throws NotFoundException;

    public void driveGames(String sourcePath) throws SolutionInvalidException;

    public boolean[][] verifyGame(int[][] game); // throw

    public int[][] solveGame(int[][] game); // throw

    public void logUserAction(UserAction userAction); // throw
}
