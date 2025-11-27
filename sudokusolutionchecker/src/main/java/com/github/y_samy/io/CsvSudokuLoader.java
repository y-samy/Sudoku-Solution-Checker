package com.github.y_samy.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import org.jspecify.annotations.NonNull;

public class CsvSudokuLoader implements SudokuLoader {

    /**
     * 
     * {@inheritDoc}
     * 
     * @param filePath valid relative or absolute path to a CSV file with valid
     *                 (9x9) integer contents without any text or text header;
     *                 {@code NonNull}
     * 
     */
    public int @NonNull [] @NonNull [] load(@NonNull String filePath) throws IOException, NumberFormatException {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            @SuppressWarnings("null") // inner lambdas[' to.Array()] will never return any null [sub]array
            int @NonNull [] @NonNull [] game = reader.lines().map(
                    row -> {
                        // row: single String --> String[] of cells
                        var cellStream = Arrays.stream(row.split("(\\s*),(\\s*)"));
                        // row: String[] --> Integer[]
                        return cellStream.mapToInt(Integer::parseInt).toArray();
                    }).toArray(int[][]::new);
            return game;
        }
    }
}
