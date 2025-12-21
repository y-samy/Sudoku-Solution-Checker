package com.github.y_samy;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.github.y_samy.gui.MainWindow;

public class Main {

    public static void main(String[] args) {
        FlatMacDarkLaf.setup();
        @SuppressWarnings("unused")
        var window = new MainWindow();
    }
}