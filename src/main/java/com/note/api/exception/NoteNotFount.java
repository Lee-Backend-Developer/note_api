package com.note.api.exception;

public class NoteNotFount extends RuntimeException {
    private final static String MESSAGE = "노트을 찾을수 없습니다.";

    public NoteNotFount() {
        super(MESSAGE);
    }
}
