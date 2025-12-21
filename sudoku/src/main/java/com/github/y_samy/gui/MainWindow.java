package com.github.y_samy.gui;

import javax.swing.JOptionPane;

import com.github.y_samy.gui.controller.MasterController;
import com.github.y_samy.gui.view.MasterView;
import com.github.y_samy.io.storage.Storage;

public class MainWindow extends javax.swing.JFrame {

    private MasterView masterView;
    @SuppressWarnings("unused")
    private MasterController masterController;

    public MainWindow(Storage gameStorage) {
        masterView = new MasterView();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        add(masterView);
        pack();

        masterController = new MasterController(gameStorage, masterView);
        setVisible(true);
    }

    public static void showStorageFailure() {
        JOptionPane.showMessageDialog(null, "Failed to initialize storage. Exiting..",
                "Sudoku failed to start",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }
}
