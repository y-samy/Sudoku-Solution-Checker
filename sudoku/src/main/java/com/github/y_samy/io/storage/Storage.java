package com.github.y_samy.io.storage;

import java.io.IOException;
import java.util.List;

public interface Storage {
    public boolean hasCurrentPuzzle() throws MalformedStorageException;

    public boolean hasNewPuzzles() throws MalformedStorageException;

    public int[][] readCurrentPuzzle() throws IOException;

    public List<String> readCurrentProgress() throws IOException;

    public void addUserAction(String userAction) throws IOException;

    public void removeLastAction() throws IOException;

    public String getCurrentGameDifficulty() throws IOException;

    public int[][] loadAndStartPuzzle(String difficulty) throws MalformedStorageException;

    public void savePuzzle(String difficulty, String identifier, int[][] board) throws IOException;

    public int[][] loadSolvedBoard(String solvedGamePath) throws IOException;
}
