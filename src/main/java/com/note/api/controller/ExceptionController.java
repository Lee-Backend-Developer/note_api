package com.note.api.controller;

import com.note.api.exception.MemberDuplication;
import com.note.api.exception.NoteApi;
import com.note.api.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoteApi.class)
    public ResponseEntity exception(NoteApi e){
        HttpStatus resolve = HttpStatus.resolve(e.getStatusCode());
        return new ResponseEntity(resolve);
    }

    @ExceptionHandler(MemberDuplication.class)
    public ResponseEntity<ErrorResponse> memberDuplication(NoteApi e){
        int statusCode = e.getStatusCode();
        ErrorResponse body = ErrorResponse.builder()
                .message(e.getMessage())
                .code(statusCode)
                .build();

        ResponseEntity<ErrorResponse> response = ResponseEntity.status(statusCode)
                .body(body);

        return response;
    }
}
