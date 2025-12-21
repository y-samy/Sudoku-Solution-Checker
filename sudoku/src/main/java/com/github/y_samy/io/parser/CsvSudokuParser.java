package com.github.y_samy.io.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import org.jspecify.annotations.NonNull;

public class CsvSudokuParser implements FileParser {

    /**
     * 
     * {@inheritDoc}
     * 
     * @param filePath valid relative or absolute path to a CSV file with valid
     *                 (9x9) integer contents without any text or text header;
     *                 {@code NonNull}
     * 
     */
    public int @NonNull [] @NonNull [] load(String filePath) throws IOException, NumberFormatException {
        var path = Paths.get(filePath);
        var lineStream = Files.readAllLines(path).stream();
        @SuppressWarnings("null") // inner lambdas[' to.Array()] will never return any null [sub]array
        int @NonNull [] @NonNull [] game = lineStream.map(
                row -> {
                    // row: single String --> String[] of cells
                    var cellStream = Arrays.stream(row.split("(\\s*),(\\s*)"));
                    // row: String[] --> Integer[]
                    return cellStream.mapToInt(Integer::parseInt).toArray();
                }).toArray(int[][]::new);
        return game;
    }

    @Override
    public void write(Path filePath, int[][] board) throws IOException {
        Files.write(filePath,
                Arrays.stream(board).map(row -> row.toString().replace("[", "").replace("]", "")).toList(),
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }
}
