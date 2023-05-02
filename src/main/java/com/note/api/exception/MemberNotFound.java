package com.note.api.exception;

public class MemberNotFound extends RuntimeException{
    private final static String MESSAGE = "회원을 찾을수 없습니다.";

    public MemberNotFound() {
        super(MESSAGE);
    }
}
