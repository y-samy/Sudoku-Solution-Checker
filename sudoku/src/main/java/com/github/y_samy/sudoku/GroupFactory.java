package com.github.y_samy.sudoku;

import java.util.ArrayList;

import org.jspecify.annotations.NonNull;

public class GroupFactory {

    public static @NonNull Group createRow(int @NonNull [] @NonNull [] board, int position) {
        return new Group(board[position - 1], position, Group.GroupType.ROW);
    }

    public static @NonNull Group createColumn(int @NonNull [] @NonNull [] board, int position) {
        return new Group(getColumnAt(board, position), position, Group.GroupType.COLUMN);
    }

    public static @NonNull Group createBox(int @NonNull [] @NonNull [] board, int position) {
        return new Group(getBoxAt(board, position), position, Group.GroupType.BOX);
    }

    public static @NonNull ArrayList<Group> getAllRows(int @NonNull [] @NonNull [] board) {
        var rows = new ArrayList<Group>(9);
        for (int i = 1; i <= 9; i++) {
            rows.add(createRow(board, i));
        }
        return rows;
    }

    public static @NonNull ArrayList<Group> getAllColumns(int @NonNull [] @NonNull [] board) {
        var columns = new ArrayList<Group>(9);
        for (int i = 1; i <= 9; i++) {
            columns.add(createColumn(board, i));
        }
        return columns;
    }

    public static @NonNull ArrayList<Group> getBoard(int @NonNull [] @NonNull [] board) {
        var boardGroups = new ArrayList<Group>();
        for (int i = 1; i <= 9; i++) {
            boardGroups.add(createRow(board, i));
            boardGroups.add(createColumn(board, i));
            boardGroups.add(createBox(board, i));
        }
        return boardGroups;
    }

    public static @NonNull ArrayList<Group> getAllBoxes(int @NonNull [] @NonNull [] board) {
        var boxes = new ArrayList<Group>(9);
        for (int i = 1; i <= 9; i++) {
            boxes.add(createRow(board, i));
        }
        return boxes;
    }

    private static int @NonNull [] getColumnAt(int @NonNull [] @NonNull [] board, int position) {
        var col = new int[9];
        for (int i = 0; i < 9; i++) {
            col[i] = board[position - 1][i];
        }
        return col;
    }

    private static int @NonNull [] getBoxAt(int @NonNull [] @NonNull [] board, int position) {
        var box = new int[9];
        // box 0: rows 0 -> 2, cols 0 -> 2;
        // box 1: rows 0 -> 2, cols 3 -> 5;
        // box 2: rows 0 -> 2, cols 6 -> 8;
        // box 3: rows 3 -> 5, cols 0 -> 2;
        // idx = position - 1;
        // colStartIdx = (box % 3) * 3;
        // rowStartIdx = (box / 3) * 3;
        position -= 1;
        int rowStartIdx = (position / 3) * 3;
        int colStartIdx = (position % 3) * 3;
        int i = 0;
        for (int r = rowStartIdx; r < rowStartIdx + 3; r += 1) {
            for (int c = colStartIdx; c < colStartIdx + 3; c += 1) {
                box[i] = board[r][c];
                i += 1;
            }
        }
        return box;
    }
}
