package com.github.y_samy.sudoku;

public class PuzzleFactory {

    private PuzzleFactory() {
        throw new IllegalCallerException();
    }

    public static int[][] generatePuzzle(Game referenceGame, DifficultyEnum difficulty) {
        var rng = new RandomPairs();
        var strippedBoard = cloneBoard(referenceGame.getBoard());
        var pairs = rng.generateDistinctPairs(difficulty.getMissingCells());
        for (var pair : pairs)
            strippedBoard[pair[0]][pair[1]] = 0;
        return strippedBoard;
    }

    private static int[][] cloneBoard(int[][] board) {
        var clone = new int[9][9];
        for (var i = 0; i < 9; i++)
            clone[i] = board[i].clone();
        return clone;
    }

}
