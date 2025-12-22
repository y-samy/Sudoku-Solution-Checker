package com.github.y_samy.gui.mastercontroller;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.github.y_samy.gui.MainWindow;
import com.github.y_samy.gui.model.Catalog;
import com.github.y_samy.gui.model.Driver;
import com.github.y_samy.gui.model.Loader;
import com.github.y_samy.gui.model.NotFoundException;
import com.github.y_samy.gui.model.SolutionInvalidException;
import com.github.y_samy.gui.view.MasterView;
import com.github.y_samy.gui.view.board.BoardView;
import com.github.y_samy.gui.view.keypad.KeypadView;
import com.github.y_samy.gui.view.mainmenu.MainMenuView;
import com.github.y_samy.io.storage.Storage;
import com.github.y_samy.sudoku.DifficultyEnum;
import com.github.y_samy.sudoku.Game;
import com.github.y_samy.sudoku.Solver;

public class MasterController {

    private MasterView view;
    private KeypadView keypad;
    private BoardView board;
    private MainMenuView mainmenu;
    private Loader loader;
    private Driver driver;
    private Catalog catalog;
    private Storage storage;
    private Game displayedGame;

    public MasterController(Storage gameStorage, MasterView view) {
        loader = new Loader(gameStorage);
        driver = new Driver(gameStorage);
        catalog = new Catalog(gameStorage);
        storage = gameStorage;
        this.view = view;
        board = view.getBoardView();
        keypad = view.getKeypadView();
        mainmenu = view.getMainMenuView();
        registerCallbacks();
        displayMainMenu();
    }

    private void registerCallbacks() {
        mainmenu.setOnContinueGame(() -> {
            try {
                displayGame(getCurrentGame());
            } catch (NotFoundException e) {
                showMainMenuError("A storage error has ocurred.");
            }
        });

        mainmenu.setOnStartNewGame((String difficulty) -> {
            try {
                displayGame(getGame(DifficultyEnum.valueOf(difficulty)));
            } catch (NotFoundException e) {
                showMainMenuError("A storage error has ocurred.");
            }
        });

        mainmenu.setOnAddToCatalog((String filePath) -> {
            try {
                driveGames(filePath);
                updateMainMenu();
            } catch (SolutionInvalidException e) {
                showMainMenuError("An invalid solution or solution path was provided.");
            }
        });

        board.setSelectionCallback(() -> {
            toggleSelection(board.getSelectedCell() != null);
        });

        keypad.setOnModify((value) -> {
            var prev = displayedGame.getValueAt(board.getSelectedRow(), board.getSelectedColumn());
            var userAction = "(" + board.getSelectedRow() + ", " + board.getSelectedColumn() + ", " + value + ", "
                    + prev + ")";
            displayedGame.setValueAt(board.getSelectedRow(), board.getSelectedColumn(), value);
            try {
                logUserAction(userAction);
                updateGameDisplay();
            } catch (IOException e) {
                showMainMenuError("A storage error has ocurred.");
            }
        });

        keypad.setOnSolve(() -> {
            var solver = new Solver(displayedGame);
            solver.solveGame(displayedGame);
            if (!solver.isValid()) {
                board.showDeadlockPopup();
            } else {
                displayedGame = solver.getSolution();
                updateGameDisplay();
            }
        });

        keypad.setOnUndo(() -> {
            try {
                undoLastLog();
                displayedGame.undo(false);
                updateGameDisplay();
            } catch (IOException e) {
                showMainMenuError("A storage error has ocurred.");
            }
        });
    }

    private void toggleSelection(boolean enabled) {
        for (int i = 1; i <= 9; i++)
            keypad.setEnabled(i, enabled);
        keypad.setEraseEnabled(enabled);
    }

    private void displayGame(Game game) {
        displayedGame = game;
        updateGameDisplay();
        view.showGame();
    }

    private void gameIsSolved() {
        try {
            loader.endGame();
            board.showSolvedPopup();
        } catch (IOException e) {
            MainWindow.showStorageFailure();
        }
        displayMainMenu();
    }

    private void updateGameDisplay() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                board.setCellValue(r, c, displayedGame.getValueAt(r, c));
                board.setCellSelectable(r, c, displayedGame.modifiable(r, c));
                board.markValid(r, c);
            }
        }
        var invalidCells = displayedGame.getInvalid();
        for (int invalidCellIdx : invalidCells)
            board.markInvalid(invalidCellIdx / 9, invalidCellIdx % 9);
        keypad.setUndoEnabled(displayedGame.canUndo(false));
        keypad.setSolveEnabled(displayedGame.getEmptyCellPositions().size() == 5
                && invalidCells.size() == 0);
        if (invalidCells.size() == 0 && displayedGame.getEmptyCellPositions().size() == 0)
            gameIsSolved();
    }

    private void updateMainMenu() {
        try {
            mainmenu.setContinueBtnEnabled(getCatalog().current());
            mainmenu.setNewGameBtnEnabled(getCatalog().allModesExist());
        } catch (IOException e) {
            MainWindow.showStorageFailure();
        }
    }

    private void displayMainMenu() {
        updateMainMenu();
        view.showMainMenu();
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public Game getCurrentGame() throws NotFoundException {
        return loader.readCurrentGame();
    }

    public Game getGame(DifficultyEnum level) throws NotFoundException {
        return loader.startNewGame(level);
    }

    public void driveGames(Game sourceGame) throws SolutionInvalidException {
        if (!sourceGame.isValid())
            throw new SolutionInvalidException();
        try {
            driver.driveGames(sourceGame);
        } catch (IOException ex) {
            throw new SolutionInvalidException();
        }
    }

    public void driveGames(String sourceGame) throws SolutionInvalidException {
        try {
            driveGames(Game.createSolutionBoard(storage.loadSolvedBoard(sourceGame)));
        } catch (IOException e) {
            throw new SolutionInvalidException();
        }
    }

    public void logUserAction(String userAction) throws IOException {
        storage.addUserAction(userAction);
    }

    public void undoLastLog() throws IOException {
        storage.removeLastAction();
    }

    private void showMainMenuError(String error) {
        JOptionPane.showMessageDialog(null, error,
                "An error ocurred",
                JOptionPane.ERROR_MESSAGE);
        displayMainMenu();
    }

}
