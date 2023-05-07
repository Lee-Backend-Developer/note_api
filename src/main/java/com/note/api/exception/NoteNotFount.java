package com.note.api.exception;

public class NoteNotFount extends NoteApi {
    private final static String MESSAGE = "노트을 찾을수 없습니다.";

    public NoteNotFount() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
