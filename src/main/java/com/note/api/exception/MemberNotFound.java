package com.note.api.exception;

public class MemberNotFound extends NoteApi{
    private final static String MESSAGE = "회원을 찾을수 없습니다.";

    public MemberNotFound() {
        super(MESSAGE);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
