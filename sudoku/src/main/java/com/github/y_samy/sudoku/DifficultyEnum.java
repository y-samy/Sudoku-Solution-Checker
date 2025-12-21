package com.github.y_samy.sudoku;

public enum DifficultyEnum {
    EASY(10),
    MEDIUM(20),
    HARD(25);

    private int missingCells;

    private DifficultyEnum(int missingCells) {
        this.missingCells = missingCells;
    }

    public int getMissingCells() {
        return this.missingCells;
    }
}
