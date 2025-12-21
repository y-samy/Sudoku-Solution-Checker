package com.github.y_samy.io.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.y_samy.io.parser.FileParser;

public class FileStorage implements Storage {

    private File rootFolder;
    private File currentGameFolder;
    private File logfile;
    private Map<String, File> puzzleFolders;
    private FileParser parser;

    public FileStorage(FileParser parser, String[] difficulties) throws MalformedStorageException {
        this.parser = parser;
        rootFolder = new File("Sudoku Storage");
        ensureFolder(rootFolder);

        currentGameFolder = new File(rootFolder, "incomplete");
        ensureFolder(currentGameFolder);
        logfile = new File(currentGameFolder, "logfile");
        puzzleFolders = new HashMap<>();
        for (var difficulty : difficulties) {
            var folder = new File(rootFolder, difficulty);
            ensureFolder(folder);
            puzzleFolders.put(difficulty, folder);
        }
    }

    private void ensureFolder(File folder) throws MalformedStorageException {
        if (!folder.isDirectory() && !folder.mkdir())
            throw new MalformedStorageException();
    }

    @Override
    public boolean hasCurrentPuzzle() {
        return (currentGameFolder.exists() && currentGameFolder.list().length == 2);
    }

    @Override
    public boolean hasNewPuzzles() {
        var puzzleLists = new ArrayList<ArrayList<String>>();
        for (var folder : puzzleFolders.values()) {
            puzzleLists.add(new ArrayList<>(
                    Arrays.stream(folder.list()).map(fileName -> fileName.split("-")[1]).toList()));
        }
        if (puzzleLists.size() == 0)
            return false;
        var commonPuzzles = puzzleLists.get(0);
        for (var puzzleList : puzzleLists)
            commonPuzzles.retainAll(puzzleList);
        commonPuzzles.remove(getCurrentPuzzleId());
        return commonPuzzles.size() > 0;
    }

    public String getCurrentPuzzleId() {
        if (!hasCurrentPuzzle())
            return "";
        var filename = Arrays.stream(currentGameFolder.list()).filter(name -> !name.equals("logfile")).findFirst();
        if (filename.isEmpty())
            return "";
        return filename.get().split("-")[1];
    }

    @Override
    public int[][] readCurrentPuzzle() throws IOException {
        if (!hasCurrentPuzzle())
            throw new IOException();
        var filename = Arrays.stream(currentGameFolder.list()).filter(name -> !name.equals("logfile"))
                .findFirst().orElseThrow(IOException::new);
        var difficulty = filename.split("-")[0];
        var puzzlePath = new File(puzzleFolders.get(difficulty), filename).getAbsolutePath();
        try {
            return parser.load(puzzlePath);
        } catch (IOException e) {
            throw new IOException();
        }
    }

    @Override
    public List<String> readCurrentProgress() throws IOException {
        if (!hasCurrentPuzzle())
            throw new IOException();
        return Files.readAllLines(logfile.toPath());
    }

    @Override
    public String getCurrentGameDifficulty() throws IOException {
        if (!hasCurrentPuzzle())
            throw new IOException();
        var filename = Arrays.stream(currentGameFolder.list()).filter(name -> !name.equals("logfile"))
                .findFirst().orElseThrow(IOException::new).split("-");
        return filename[0];
    }

    @Override
    public int[][] loadAndStartPuzzle(String difficulty) throws MalformedStorageException {
        var puzzleLists = new ArrayList<List<String>>();
        for (var folder : puzzleFolders.values()) {
            puzzleLists.add(new ArrayList<>(
                    Arrays.stream(folder.list()).map(fileName -> fileName.split("-")[1]).toList()));
        }
        if (puzzleLists.size() == 0)
            throw new MalformedStorageException();
        var commonPuzzles = puzzleLists.get(0);
        for (var puzzleList : puzzleLists)
            commonPuzzles.retainAll(puzzleList);
        var fullPuzzleId = difficulty + "-" + commonPuzzles.get(0);
        var puzzleFile = new File(puzzleFolders.get(difficulty),
                fullPuzzleId);
        var newLogFile = new File(currentGameFolder, "logfile");
        var newCurrentGame = new File(currentGameFolder, fullPuzzleId);
        try {
            Files.copy(puzzleFile.toPath(), newCurrentGame.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            newLogFile.delete();
            if (!newLogFile.createNewFile())
                throw new IOException();
            return parser.load(puzzleFile.getAbsolutePath());
        } catch (IOException e) {
            newCurrentGame.delete();
            newLogFile.delete();
            throw new MalformedStorageException();
        }
    }

    @Override
    public void savePuzzle(String difficulty, String identifier, int[][] board) throws IOException {
        var file = new File(puzzleFolders.get(difficulty),
                difficulty + "-" + identifier);
        parser.write(file.toPath(), board);
    }

    @Override
    public int[][] loadSolvedBoard(String solvedGamePath)
            throws IOException {
        return parser.load(solvedGamePath);
    }

    @Override
    public void addUserAction(String userAction) throws IOException {
        Files.writeString(logfile.toPath(), userAction, StandardOpenOption.APPEND, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
    }

    @Override
    public void removeLastAction() throws IOException {
        var allLines = readCurrentProgress();
        allLines.removeLast();
        Files.write(logfile.toPath(), allLines, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE,
                StandardOpenOption.WRITE);
    }

}
