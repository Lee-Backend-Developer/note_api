package com.note.api.exception;

public class CategoryLimit extends NoteApi {

    private final static String MESSAGE = "5개 까지 생성할 수 있습니다.";

    public CategoryLimit() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
