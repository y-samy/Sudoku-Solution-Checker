package com.github.y_samy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.io.CsvSudokuLoader;
import com.github.y_samy.sudoku.base.SudokuGroup;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;
import com.github.y_samy.threading.FuturesSudokuChecker;

public class Main {
    private static String filePath = "";
    private static int threads;

    private static boolean parseArgs(String[] args) {
        if (args.length < 4)
            return false;
        if (args[0].equals("-f") && args[2].equals("-t")) {
            filePath = args[1];
            threads = Integer.parseInt(args[3]);
            if (threads != 0 && threads != 1 && threads != 3 && threads != 9 && threads != 27)
                return false;
            return true;
        } else if (args[2].equals("-f") && args[0].equals("-t")) {
            filePath = args[3];
            threads = Integer.parseInt(args[1]);
            if (threads != 0 && threads != 1 && threads != 3 && threads != 9 && threads != 27)
                return false;
            return true;
        }
        return false;

    }

    private static void scanArgs() {
        var sc = new Scanner(System.in);
        System.out.print("Enter filename: ");
        filePath = sc.nextLine();
        while (true) {
            System.out.print("Enter thread count: ");
            threads = sc.nextInt();
            if (threads == 0 || threads == 1 || threads == 3 || threads == 9 || threads == 27)
                break;
        }
        sc.close();
    }

    public static void main(String[] args) {
        var loader = new CsvSudokuLoader();
        var checkerFactory = new FuturesSudokuChecker();
        if (!parseArgs(args))
            scanArgs();
        int @NonNull [] @NonNull [] game = new int[0][0];
        try {
            game = loader.load(filePath);
        } catch (NumberFormatException | IOException e) {
            System.out.println("An error occured while loading the game.");
            System.exit(1);
        }
        ArrayList<SudokuGroupValidationResult> results = null;
        if (threads == 1 || threads == 0)
            results = checkerFactory.newSingleThreadValidator(game);
        else if (threads == 3)
            results = checkerFactory.newThreeThreadValidator(game);
        else if (threads == 9)
            results = checkerFactory.newNineThreadValidator(game);
        else if (threads == 27)
            results = checkerFactory.newTwentySevenThreadValidator(game);

        var invalidRows = new ArrayList<SudokuGroupValidationResult>(0);
        var invalidCols = new ArrayList<SudokuGroupValidationResult>(0);
        var invalidBoxes = new ArrayList<SudokuGroupValidationResult>(0);
        var valid = true;
        for (var result : results) {
            if (!result.isValid()) {
                valid = false;
                if (result.getGroupType() == SudokuGroup.GroupType.ROW)
                    invalidRows.add(result);
                if (result.getGroupType() == SudokuGroup.GroupType.COLUMN)
                    invalidCols.add(result);
                if (result.getGroupType() == SudokuGroup.GroupType.BOX)
                    invalidBoxes.add(result);
            }
        }

        if (valid) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            if (invalidRows.size() > 0) {
                for (var row : invalidRows) {
                    System.out
                            .println("" + row.getGroupType() + row.getGlobalPosition() + row.getInvalidCellPositions());
                }
                System.out.println("----------------");
            }
            if (invalidCols.size() > 0) {
                for (var col : invalidCols) {
                    System.out
                            .println("" + col.getGroupType() + col.getGlobalPosition() + col.getInvalidCellPositions());
                }
                System.out.println("----------------");
            }
            if (invalidBoxes.size() > 0) {
                for (var box : invalidBoxes) {
                    System.out
                            .println("" + box.getGroupType() + box.getGlobalPosition() + box.getInvalidCellPositions());
                }
                System.out.println("----------------");
            }
        }
    }
}