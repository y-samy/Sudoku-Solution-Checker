package com.github.y_samy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.io.CsvSudokuLoader;
import com.github.y_samy.sudoku.base.SudokuGroup.GroupType;
import com.github.y_samy.sudoku.base.SudokuGroupValidationResult;
import com.github.y_samy.threading.BatchFuturesSudokuChecker;
import com.github.y_samy.threading.BatchThreadedSudokuChecker;
import com.github.y_samy.threading.FuturesSudokuChecker;
import com.github.y_samy.threading.SudokuCheckerFactory;

public class Main {
    private static String filePath = "";
    private static int threads;
    private static boolean benchmark = false;
    private static SudokuCheckerFactory checkerFactory = null;

    private static boolean parseArgs(String[] argArr) {
        try {
            var args = List.of(argArr);
            if (!args.contains("-f") || !args.contains("-t"))
                return false;
            filePath = args.get(args.indexOf("-f") + 1);
            threads = Integer.parseInt(args.get(args.indexOf("-t") + 1));
            if (threads != 0 && threads != 1 && threads != 3 && threads != 9 && threads != 27)
                return false;
            benchmark = (args.contains("--benchmark") || args.contains("-b"));
            if (args.contains("-m")) {
                var type = args.get(args.indexOf("-m") + 1);
                if (type.equals("futures"))
                    checkerFactory = new FuturesSudokuChecker();
                else if (type.equals("batch-futures"))
                    checkerFactory = new BatchFuturesSudokuChecker();
                else if (type.equals("batch-threads"))
                    checkerFactory = new BatchThreadedSudokuChecker();
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
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
        if (!parseArgs(args))
            scanArgs();
        if (checkerFactory == null)
            checkerFactory = new BatchThreadedSudokuChecker();
        int @NonNull [] @NonNull [] game = new int[0][0];
        try {
            game = loader.load(filePath);
        } catch (NumberFormatException | IOException e) {
            System.out.println("An error occured while loading the game.");
            System.exit(1);
        }
        HashMap<GroupType, ArrayList<SudokuGroupValidationResult>> results = null;
        long startTime = System.nanoTime();
        if (threads == 1 || threads == 0)
            results = checkerFactory.newSingleThreadValidator(game);
        else if (threads == 3)
            results = checkerFactory.newThreeThreadValidator(game);
        else if (threads == 9)
            results = checkerFactory.newNineThreadValidator(game);
        else if (threads == 27)
            results = checkerFactory.newTwentySevenThreadValidator(game);
        long delta = System.nanoTime() - startTime;
        if (benchmark) {
            double deltaMillis = (double) delta / 1_000_000.0;
            System.out.println("Benchmark result -- time taken: " + deltaMillis + "ms");
        }

        var valid = !results.values().stream().flatMap(values -> values.stream()).filter(result -> !result.isValid())
                .findFirst().isPresent();

        if (valid) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            var printSep = false;
            for (var row : results.get(GroupType.ROW)) {
                printSep = false;
                if (!row.isValid()) {
                    System.out.println(
                            row.getGroupType() + " " + row.getGlobalPosition() + " " + row.getInvalidCellPositions());
                    printSep = true;
                }
            }
            if (printSep)
                System.out.println("----------------");
            printSep = false;
            for (var col : results.get(GroupType.COLUMN)) {

                if (!col.isValid()) {
                    System.out.println(
                            col.getGroupType() + " " + col.getGlobalPosition() + " " + col.getInvalidCellPositions());
                    printSep = true;
                }
            }
            if (printSep)
                System.out.println("----------------");
            printSep = false;
            for (var box : results.get(GroupType.BOX)) {
                if (!box.isValid()) {
                    System.out.println(
                            box.getGroupType() + " " + box.getGlobalPosition() + " " + box.getInvalidCellPositions());
                }
            }
        }
    }
}