package com.github.y_samy.gui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.github.y_samy.gui.view.board.BoardView;

import java.awt.CardLayout;

public class MainWindow extends JFrame {
    private CardLayout cardLayout;

    public MainWindow() {
        initComponents();
    }

    private void initComponents() {

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        getContentPane().setLayout(cardLayout);

        var board = new BoardView();
        getContentPane().add("e", board);
        cardLayout.show(getContentPane(), "e");
        board.setCellSelectable(0, 0, false);
        board.setCellValue(1, 1, 9);
        board.markInvalid(1, 1);
        board.markValid(1, 1);

        pack();
        setVisible(true);
    }

}
