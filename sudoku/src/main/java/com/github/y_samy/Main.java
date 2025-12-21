package com.github.y_samy;

import java.util.Arrays;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.y_samy.gui.MainWindow;
import com.github.y_samy.io.parser.CsvSudokuParser;
import com.github.y_samy.io.storage.FileStorage;
import com.github.y_samy.io.storage.MalformedStorageException;
import com.github.y_samy.sudoku.DifficultyEnum;

public class Main {

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        try {
            @SuppressWarnings("unused")
            var window = new MainWindow(new FileStorage(new CsvSudokuParser(),
                    Arrays.stream(DifficultyEnum.values()).map(d -> d.name()).toArray(String[]::new)));
        } catch (MalformedStorageException e) {
            MainWindow.showStorageFailure();
        }
    }
}