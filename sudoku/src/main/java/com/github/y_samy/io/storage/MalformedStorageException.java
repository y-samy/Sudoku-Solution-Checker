package com.github.y_samy.io.storage;

import java.io.IOException;

public class MalformedStorageException extends IOException {
    public MalformedStorageException() {

    }

    public MalformedStorageException(String message) {
        super(message);
    }
}
