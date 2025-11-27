package com.github.y_samy.io;

import java.io.IOException;

import org.jspecify.annotations.NonNull;

public interface SudokuLoader {
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
     *         board
     * 
     */
    public int @NonNull [] @NonNull [] load(@NonNull String filePath) throws IOException, NumberFormatException;
}
