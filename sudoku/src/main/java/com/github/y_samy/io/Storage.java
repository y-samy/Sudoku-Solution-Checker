package com.github.y_samy.io;

import java.io.IOException;

import org.jspecify.annotations.NonNull;

public interface Storage {
    /**
     * 
     * @throws MalformedStorageException propagates any IO exceptions
     * 
     * @return
     *         <p>
     *         {@code true} if there is a puzzle file in
     *         {puzzles_folder}/incomplete/
     *         </p>
     *         <p>
     *         {@code false} if {puzzles_folder}/incomplete/ is empty
     *         </p>
     * 
     */
    public boolean hasCurrentPuzzle() throws MalformedStorageException;

    /**
     * 
     * @throws MalformedStorageException propagates any IO exceptions
     * 
     * @return
     *         <p>
     *         {@code true} if there is a puzzle file in
     *         {puzzles_folder}/{all except incomplete}/
     *         </p>
     *         <p>
     *         {@code false} if {puzzles_folder}/{any other than incomplete}/ is
     *         empty
     *         </p>
     * 
     */
    public boolean hasNewPuzzles() throws MalformedStorageException;

    public int @NonNull [] @NonNull [] readCurrentPuzzle() throws MalformedStorageException;

    public int @NonNull [] @NonNull [] loadAndStartPuzzle(@NonNull String difficulty) throws MalformedStorageException;

    public void savePuzzle(@NonNull String difficulty, @NonNull String identifier, int[][] board) throws IOException;

    public int @NonNull [] @NonNull [] loadSolvedBoard(@NonNull String solvedGamePath) throws IOException;
}
