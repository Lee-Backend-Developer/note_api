package com.note.api.request.category;

import lombok.Builder;
import lombok.Data;

@Data
public class CategoryNameChange {
    private String name;

    public CategoryNameChange() {
    }

    @Builder
    public CategoryNameChange(String name) {
        this.name = name;
    }
}
