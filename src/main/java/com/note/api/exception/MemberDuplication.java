package com.note.api.exception;

public class MemberDuplication extends RuntimeException{
    private final static String MESSAGE = "이미 가입된 회원이 있습니다.";

    public MemberDuplication() {
        super(MESSAGE);
    }

}
