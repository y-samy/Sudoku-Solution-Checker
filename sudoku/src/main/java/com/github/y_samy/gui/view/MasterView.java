package com.github.y_samy.gui.view;

import java.awt.CardLayout;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.y_samy.gui.model.InvalidGame;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.gui.view.board.BoardView;
import com.github.y_samy.gui.view.cards.GameCard;
import com.github.y_samy.gui.view.keypad.KeypadView;
import com.github.y_samy.gui.view.mainmenu.MainMenuView;
import com.github.y_samy.sudoku.UserAction;

public class MasterView extends JPanel implements Controllable {

    private GameCard gameCard;
    private MainMenuView mainMenu;
    private CardLayout layout;

    public MasterView() {
        super(new CardLayout());
        layout = (CardLayout) getLayout();
        gameCard = new GameCard();
        mainMenu = new MainMenuView();
        add(gameCard, "BOARD");
        add(mainMenu, "MENU");
    }

    public BoardView getBoardView() {
        return gameCard.getBoardView();
    }

    public KeypadView getKeypadView() {
        return gameCard.getKeypadView();
    }

    public MainMenuView getMainMenuView() {
        return mainMenu;
    }

    public void showMainMenu() {
        layout.show(this, "MENU");
    }

    public void showGame() {
        layout.show(this, "BOARD");
    }

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
    public int[][] solveGame(int[][] game) throws InvalidGame {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'solveGame'");
    }

    @Override
    public void logUserAction(UserAction userAction) throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logUserAction'");
    }

}
