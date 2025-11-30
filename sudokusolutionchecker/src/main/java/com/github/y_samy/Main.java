package com.github.y_samy;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import org.jspecify.annotations.NonNull;

import com.github.y_samy.io.CsvSudokuLoader;
import com.github.y_samy.sudoku.Group.GroupType;
import com.github.y_samy.validation.BoardCheckerFactory;
import com.github.y_samy.validation.BoardCheckerFactory.ConcurrencyStrategy;
import com.github.y_samy.validation.base.BoardChecker;

public class Main {
    private static @NonNull String filePath = "";
    private static int threads;
    private static boolean benchmark = false;
    private static ConcurrencyStrategy strategy = ConcurrencyStrategy.THREADED;

    @SuppressWarnings("null")
    private static boolean parseArgs(String[] argArr) {
        try {
            var args = List.of(argArr);
            if (!args.contains("-f") || !args.contains("-t"))
                return false;
            filePath = args.get(args.indexOf("-f") + 1);
            threads = Integer.parseInt(args.get(args.indexOf("-t") + 1));
            if (threads != 0 && threads != 1 && threads != 3 && threads != 27)
                return false;
            benchmark = (args.contains("--benchmark") || args.contains("-b"));
            if (args.contains("-s")) {
                var type = args.get(args.indexOf("-m") + 1);
                if (type.equals("futures"))
                    strategy = ConcurrencyStrategy.FUTURES;
                else if (type.equals("batch-futures"))
                    strategy = ConcurrencyStrategy.BATCH_FUTURES;
                else if (type.equals("batch-threads"))
                    strategy = ConcurrencyStrategy.THREADED;
            }
            return true;
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
    }

    @SuppressWarnings("null")
    private static void scanArgs() {

        var sc = new Scanner(System.in);
        System.out.print("Enter filename: ");
        filePath = sc.nextLine();
        while (true) {
            System.out.print("Enter thread count: ");
            threads = sc.nextInt();
            if (threads == 0 || threads == 1 || threads == 3 || threads == 27)
                break;
        }
        sc.close();
    }

    public static void main(String[] args) {
        var loader = new CsvSudokuLoader();
        if (!parseArgs(args))
            scanArgs();
        int @NonNull [] @NonNull [] game = new int[0][0];
        try {
            game = loader.load(filePath);
        } catch (NumberFormatException | IOException e) {
            System.out.println("An error occured while loading the game.");
            System.exit(1);
        }
        BoardChecker checker = null;
        if (threads == 1 || threads == 0)
            checker = BoardCheckerFactory.newSequentialChecker(game);
        else
            checker = BoardCheckerFactory.newConcurrentChecker(game, threads, strategy);
        long startTime = System.nanoTime();
        var results = checker.validate();
        long delta = System.nanoTime() - startTime;
        if (benchmark) {
            double deltaMillis = (double) delta / 1_000_000.0;
            System.out.println("Benchmark result -- time taken: " + deltaMillis + "ms");
        }
        if (results == null)
            System.exit(1);
        @SuppressWarnings("null")
        var valid = !results.stream().filter(result -> !result.isValid()).findFirst().isPresent();
        var sortedResults = BoardChecker.sortResults(results);
        if (valid) {
            System.out.println("VALID");
        } else {
            System.out.println("INVALID");
            var printSep = false;
            for (var row : sortedResults.get(GroupType.ROW)) {
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
            for (var col : sortedResults.get(GroupType.COLUMN)) {

                if (!col.isValid()) {
                    System.out.println(
                            col.getGroupType() + " " + col.getGlobalPosition() + " " + col.getInvalidCellPositions());
                    printSep = true;
                }
            }
            if (printSep)
                System.out.println("----------------");
            printSep = false;
            for (var box : sortedResults.get(GroupType.BOX)) {
                if (!box.isValid()) {
                    System.out.println(
                            box.getGroupType() + " " + box.getGlobalPosition() + " " + box.getInvalidCellPositions());
                }
            }
        }
    }
}