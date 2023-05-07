package com.note.api.exception;

public class CategoryNotFount extends NoteApi{
    private final static String MESSAGE = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFount() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }

}
