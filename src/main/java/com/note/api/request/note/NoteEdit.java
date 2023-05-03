package com.note.api.request.note;


import com.note.api.entity.Category;
import lombok.Builder;
import lombok.Data;

@Data @Builder
public class NoteEdit {
    private String content;
    private Category category;
}
