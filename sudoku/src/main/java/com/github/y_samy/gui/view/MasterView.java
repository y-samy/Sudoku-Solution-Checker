package com.github.y_samy.gui.view;

import java.awt.CardLayout;

import javax.swing.JPanel;

import com.github.y_samy.gui.view.board.BoardView;
import com.github.y_samy.gui.view.cards.GameCard;
import com.github.y_samy.gui.view.keypad.KeypadView;
import com.github.y_samy.gui.view.mainmenu.MainMenuView;

public class MasterView extends JPanel {

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
}
