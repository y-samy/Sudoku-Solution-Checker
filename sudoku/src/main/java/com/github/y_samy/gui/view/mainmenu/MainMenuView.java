package com.github.y_samy.gui.view.mainmenu;

import java.io.File;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.github.y_samy.sudoku.DifficultyEnum;

public class MainMenuView extends javax.swing.JPanel {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton continueBtn;
    private javax.swing.JButton loadSolutionBtn;
    private javax.swing.JButton newGameBtn;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    private DriveGamesCallback onAddToCatalog = (s) -> {
    };
    private LoadGameCallback onStartNewGame = (d) -> {
    };
    private ContinueGameCallback onContinueGame = () -> {
    };

    public MainMenuView(ContinueGameCallback onContinueGame, LoadGameCallback onStartNewGame,
            DriveGamesCallback onAddToCatalog) {
        if (onContinueGame != null)
            this.onContinueGame = onContinueGame;
        if (onStartNewGame != null)
            this.onStartNewGame = onStartNewGame;
        if (onAddToCatalog != null)
            this.onAddToCatalog = onAddToCatalog;
        initComponents();
    }

    public MainMenuView() {
        this(null, null, null);
    }

    public DriveGamesCallback getOnAddToCatalog() {
        return onAddToCatalog;
    }

    public void setContinueBtnEnabled(boolean enabled) {
        continueBtn.setEnabled(enabled);
    }

    public void setNewGameBtnEnabled(boolean enabled) {
        newGameBtn.setEnabled(enabled);
    }

    public void setLoadSolutionEnabled(boolean enabled) {
        loadSolutionBtn.setEnabled(enabled);
    }

    public void setOnAddToCatalog(DriveGamesCallback onAddToCatalog) {
        if (onAddToCatalog != null)
            this.onAddToCatalog = onAddToCatalog;
        else
            this.onAddToCatalog = (s) -> {
            };
    }

    public LoadGameCallback getOnStartNewGame() {
        return onStartNewGame;
    }

    public void setOnStartNewGame(LoadGameCallback onStartNewGame) {
        if (onStartNewGame != null)
            this.onStartNewGame = onStartNewGame;
        else
            this.onStartNewGame = (d) -> {
            };
    }

    public ContinueGameCallback getOnContinueGame() {
        return onContinueGame;
    }

    public void setOnContinueGame(ContinueGameCallback onContinueGame) {
        if (onContinueGame != null)
            this.onContinueGame = onContinueGame;
        else
            this.onContinueGame = () -> {
            };
    }

    private void showFilePickerDialog() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select a file");
        chooser.setCurrentDirectory(new File("."));
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Solution File", "csv"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            getOnAddToCatalog().callback(file.getAbsolutePath());
        }
    }

    private void showDifficultyPickerDialog() {
        var difficulties = Arrays.stream(DifficultyEnum.values()).map(d -> d.name()).toArray(String[]::new);
        String selected = (String) JOptionPane.showInputDialog(
                null,
                "Select difficulty:",
                "Starting new game",
                JOptionPane.QUESTION_MESSAGE,
                null,
                difficulties,
                difficulties[0]);
        if (selected != null)
            getOnStartNewGame().callback(selected);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        titleLabel = new javax.swing.JLabel();
        continueBtn = new javax.swing.JButton();
        newGameBtn = new javax.swing.JButton();
        loadSolutionBtn = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        titleLabel.setFont(titleLabel.getFont().deriveFont(titleLabel.getFont().getStyle() | java.awt.Font.BOLD,
                titleLabel.getFont().getSize() + 11));
        titleLabel.setText("Sudoku");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(7, 0, 7, 0);
        add(titleLabel, gridBagConstraints);

        continueBtn.setFont(continueBtn.getFont().deriveFont(continueBtn.getFont().getSize() + 3f));
        continueBtn.setText("Continue Playing");
        continueBtn.addActionListener(this::continueBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(7, 8, 7, 8);
        add(continueBtn, gridBagConstraints);

        newGameBtn.setFont(newGameBtn.getFont().deriveFont(newGameBtn.getFont().getSize() + 3f));
        newGameBtn.setText("New Game");
        newGameBtn.addActionListener(this::newGameBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(7, 8, 7, 8);
        add(newGameBtn, gridBagConstraints);

        loadSolutionBtn.setFont(loadSolutionBtn.getFont().deriveFont(loadSolutionBtn.getFont().getSize() + 3f));
        loadSolutionBtn.setText("Add To Catalog");
        loadSolutionBtn.addActionListener(this::loadSolutionBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 61;
        gridBagConstraints.ipady = 17;
        gridBagConstraints.insets = new java.awt.Insets(7, 8, 7, 8);
        add(loadSolutionBtn, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void continueBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_continueBtnActionPerformed
        getOnContinueGame().callback();
    }// GEN-LAST:event_continueBtnActionPerformed

    private void newGameBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_newGameBtnActionPerformed
        showDifficultyPickerDialog();
    }// GEN-LAST:event_newGameBtnActionPerformed

    private void loadSolutionBtnActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_loadSolutionBtnActionPerformed
        showFilePickerDialog();
    }// GEN-LAST:event_loadSolutionBtnActionPerformed
}
