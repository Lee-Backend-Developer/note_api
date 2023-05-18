package com.note.api.exception;

public class NoteNotEqual extends NoteApi{

    private final static String MESSAGE = "작성자가 아닙니다.";

    public NoteNotEqual() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
