package com.note.api.exception;

public abstract class NoteApi extends RuntimeException {
    public NoteApi(String message) {
        super(message);
    }

    public abstract int getStatusCode();
}
