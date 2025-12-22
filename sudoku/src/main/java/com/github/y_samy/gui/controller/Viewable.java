package com.github.y_samy.gui.controller;

import java.io.IOException;

import com.github.y_samy.gui.model.Catalog;
import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;

public interface Viewable {
    public Catalog getCatalog();

    public Game getGame(DifficultyEnum level) throws NotFoundException;

    public void driveGames(Game sourceGame) throws SolutionInvalidException;

    public String verifyGame(Game game);

    public int[] solveGame(Game game) throws InvalidGame;

    public void logUserAction(String userAction) throws IOException;
}
