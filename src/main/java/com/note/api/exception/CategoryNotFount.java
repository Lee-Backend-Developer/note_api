package com.note.api.exception;

public class CategoryNotFount extends RuntimeException{
    private final static String MESSAGE = "카테고리를 찾을 수 없습니다.";

    public CategoryNotFount() {
        super(MESSAGE);
    }

}
