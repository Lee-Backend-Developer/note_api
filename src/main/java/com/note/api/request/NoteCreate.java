package com.note.api.request;


import com.note.api.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class NoteCreate {
    private String content;
    private Category category;
}
