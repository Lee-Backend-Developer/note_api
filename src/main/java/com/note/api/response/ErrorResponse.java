package com.note.api.response;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ErrorResponse {
    private String message;
    private int code;
}
