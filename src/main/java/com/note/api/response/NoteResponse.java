package com.note.api.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NoteResponse {
    private String category;
    private String content;

}