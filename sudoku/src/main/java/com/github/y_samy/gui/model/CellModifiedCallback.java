package com.github.y_samy.gui.view;

public interface CellModifiedCallback {
    public void callback(int row, int column, int previous, int updated);
}
