package com.github.y_samy.sudoku;

public class UserAction {
    public int row, column, previousValue, currentValue;

    public UserAction(int row, int column, int currentValue, int previousValue) {
        this.row = row;
        this.column = column;
        this.previousValue = previousValue;
        this.currentValue = currentValue;
    }
}
