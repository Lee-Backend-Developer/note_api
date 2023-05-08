package com.note.api.request.category;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
public class CategoryCreate {
    private String name;

    public CategoryCreate() {
    }

    @Builder
    public CategoryCreate(String name) {
        this.name = name;
    }
}
