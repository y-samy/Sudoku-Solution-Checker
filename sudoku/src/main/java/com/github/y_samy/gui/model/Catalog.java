package com.github.y_samy.gui.model;

import java.io.IOException;

import com.github.y_samy.io.storage.Storage;

public class Catalog {

    private Storage gameStorage;

    public Catalog(Storage gameStorage) {
        this.gameStorage = gameStorage;
    }

    public boolean current() throws IOException {
        return gameStorage.hasCurrentPuzzle();
    }

    public boolean allModesExist() throws IOException {
        return gameStorage.hasNewPuzzles();
    }
}
