package com.github.y_samy.gui.controller;

import java.io.IOException;

import com.github.y_samy.gui.model.Catalog;
import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;

public interface Viewable {
    Catalog getCatalog();

    Game getGame(DifficultyEnum level) throws NotFoundException;

    void driveGames(Game sourceGame) throws SolutionInvalidException;

    String verifyGame(Game game);

    int[] solveGame(Game game) throws InvalidGame;

    void logUserAction(String userAction) throws IOException;

}
