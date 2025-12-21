package com.github.y_samy.gui.view;

import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.sudoku.UserAction;

public class MasterView implements Controllable {

    @Override
    public boolean[] getCatalog() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCatalog'");
    }

    @Override
    public int[][] getGame(char level) throws NotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGame'");
    }

    @Override
    public void driveGames(String sourcePath) throws SolutionInvalidException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'driveGames'");
    }

    @Override
    public boolean[][] verifyGame(int[][] game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyGame'");
    }

    @Override
    public int[][] solveGame(int[][] game) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }

    @Override
    public void logUserAction(UserAction userAction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logUserAction'");
    }

}
