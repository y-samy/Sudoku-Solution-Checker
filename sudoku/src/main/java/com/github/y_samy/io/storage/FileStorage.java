package com.github.y_samy.io.storage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.github.y_samy.io.parser.FileParser;

public class FileStorage implements Storage {

    private File rootFolder;
    private File currentGameFolder;
    private File logfile;
    private Map<String, File> puzzleFolders;
    private FileParser parser;

    public FileStorage(FileParser parser) throws MalformedStorageException {
        this.parser = parser;
        rootFolder = new File("Sudoku Storage");
        ensureFolder(rootFolder);

        currentGameFolder = new File(rootFolder, "incomplete");
        ensureFolder(currentGameFolder);
        logfile = new File(currentGameFolder, "logfile");
        var difficultyFolders = List.of(rootFolder.listFiles());
        difficultyFolders.removeIf(folder -> folder.getName().equals("incomplete"));
        for (var folder : difficultyFolders) {
            puzzleFolders.put(folder.getName(), folder);
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
        var puzzleLists = new ArrayList<List<String>>();
        for (var folder : puzzleFolders.values()) {
            puzzleLists.add(Arrays.stream(folder.list()).map(fileName -> fileName.split("-")[1]).toList());
        }
        var commonPuzzles = puzzleLists.get(0);
        for (var puzzleList : puzzleLists)
            commonPuzzles.retainAll(puzzleList);
        return commonPuzzles.size() > 0;
    }

    public int[][] readCurrentGame() throws MalformedStorageException {
        if (!hasCurrentPuzzle())
            throw new MalformedStorageException();
        var filename = Arrays.stream(currentGameFolder.list()).filter(name -> !name.equals("logfile")).findFirst();
        if (filename.isEmpty())
            throw new MalformedStorageException();
        var filepath = new File(currentGameFolder, filename.get()).getAbsolutePath();
        try {
            return parser.load(filepath);
        } catch (IOException e) {
            throw new MalformedStorageException();
        }
    }

    @Override
    public int[][] readCurrentPuzzle() throws IOException {
        if (!hasCurrentPuzzle())
            throw new IOException();
        var filename = Arrays.stream(currentGameFolder.list()).filter(name -> !name.equals("logfile"))
                .findFirst().orElseThrow(IOException::new).split("-");
        var difficulty = filename[0];
        var puzzleId = filename[1];
        var puzzlePath = new File(puzzleFolders.get(difficulty), puzzleId).getAbsolutePath();
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
            puzzleLists.add(Arrays.stream(folder.list()).map(fileName -> fileName.split("-")[1]).toList());
        }
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
