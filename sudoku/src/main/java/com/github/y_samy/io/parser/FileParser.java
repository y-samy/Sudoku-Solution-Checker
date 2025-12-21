package com.github.y_samy.io.parser;

import java.io.IOException;
import java.nio.file.Path;

public interface FileParser {
    /**
     * 
     * @param filePath relative or absolute path to valid game solution
     *                 file with valid contents; {@code NonNull}
     * 
     * @throws IOException           propagates {@code FileNotFound} upcast to
     *                               {@code IOException} to notify caller of errors.
     *                               Resources are released even if the exception is
     *                               propagated
     * 
     * @throws NumberFormatException can occur if file contents are invalid
     * 
     * @return non-null; {@code Integer[9][9]} array of the cells on the Sudoku
     *         board, cells are assumed to already have values between 0 and 9
     * 
     */
    public int[][] load(String filePath) throws IOException, NumberFormatException;

    public void write(Path filePath, int[][] board) throws IOException;
}
