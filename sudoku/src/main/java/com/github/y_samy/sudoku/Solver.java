package com.github.y_samy.sudoku;

public class Solver {

    private Combinations combinations;
    private final int[][] flyweightBoard;
    private int[] solutionCombination;
    private Game solution;

    public Solver(Game source) {
        combinations = new Combinations();
        flyweightBoard = source.copyBoard();
    }

    public void solveGame(Game source) {
        Game attemptedSolution = null;
        for (var c : combinations) {
            attemptedSolution = new Game(flyweightBoard, c);
            if (attemptedSolution.isValid()) {
                solution = attemptedSolution;
                solutionCombination = c;
                return;
            }
        }
    }

    public boolean isValid() {
        return (solution != null);
    }

    public Game getSolution() {
        return solution;
    }

    public int[] getSolutionCombination() {
        return solutionCombination;
    }
}
